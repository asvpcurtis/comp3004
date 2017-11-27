using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Collections;
using TournamentMasterAPI.Models;
namespace TournamentMasterAPI.Builders
{
    public enum TournamentFormat
    {
        Swiss,
        Elimination,
        DoubleElimination,
        RoundRobin
    }
    public static class TournamentBuilder
    {
        public static void InititializeTournament(TournamentMasterDBContext context, IEnumerable<Competitor> competitors, Tournament tournament, string seeding)
        {
            if (competitors.Count() < 2)
            {
                return;
            }
            // Seed the competitors
            IEnumerable<Competitor> seededCompetitors = competitors;
            if (seeding == "random")
            {
                Random r = new Random();
                IEnumerable<Competitor> randomizedCompetitors = competitors.OrderBy(c => r.Next()).ToArray();
            }
            else if (seeding == "rating")
            {
                seededCompetitors = competitors.OrderByDescending(c => c.Rating);
            }

            // Create relationship with tournament
            int seed = 1;
            foreach (Competitor c in seededCompetitors)
            {
                CompetitorTournament relationship = new CompetitorTournament
                {
                    CompetitorId = c.Id,
                    Tournament = tournament,
                    Seed = seed
                };
                context.CompetitorTournament.Add(relationship);
                seed++;
            }
            context.SaveChanges();
            UpdateTournament(context, tournament);
        }

        public static void UpdateTournament(TournamentMasterDBContext context, Tournament tournament)
        {
            // Get latest round if there is one else null
            Round currentRound = context.Rounds.Where(r => r.TournamentId == tournament.Id)
                .OrderByDescending(r => r.RoundNumber)
                .FirstOrDefault();

            bool currentRoundComplete;

            // There is a Round to check
            if (currentRound != null)
            {
                // Get all pairings that belong to this round
                IEnumerable<Pairing> currentRoundPairings = context.Pairings.Where(p => p.RoundId == currentRound.Id);

                // Check that all Pairings (that aren't byes) have a result
                currentRoundComplete = currentRoundPairings.All(p => p.Result == p.CompetitorId1 || p.Result == p.CompetitorId2);
            }
            // There is no round yet
            else
            {
                currentRoundComplete = true;
            }
            if (currentRoundComplete)
            {
                switch (tournament.Format)
                {
                    case (int)TournamentFormat.Swiss:
                        CreateSwissRound(context, tournament);
                        break;
                    case (int)TournamentFormat.Elimination:
                        CreateEliminationRound(context, tournament);
                        break;
                    case (int)TournamentFormat.DoubleElimination:
                        CreateDoubleEliminationRound(context, tournament);
                        break;
                    case (int)TournamentFormat.RoundRobin:
                        CreateRoundRobinRound(context, tournament);
                        break;
                }
            }
        }

        private static void CreateEliminationRound(TournamentMasterDBContext context, Tournament tournament)
        {
            // Get latest round if there is one else null
            Round lastRound = context.Rounds.Where(r => r.TournamentId == tournament.Id)
                .OrderByDescending(r => r.RoundNumber)
                .FirstOrDefault();

            if (lastRound == null)
            {
                // Get all Competitors ordered by seed
                List<Competitor> competitors = context.CompetitorTournament
                    .Where(ct => ct.TournamentId == tournament.Id)
                    .OrderByDescending(ct => ct.Seed)
                    .Select(ct => ct.Competitor)
                    .ToList();

                // we need to introduce byes for the first round if num players != 2^n
                int playerCount = competitors.Count();

                // determine what "n" should be
                int n = (int)Math.Log(playerCount, 2) + 1;

                int numByes = n - playerCount;

                for (int i = 0; i < numByes; i++)
                {
                    competitors.Add(null);
                }
                Round newRound = new Round
                {
                    RoundNumber = 1,
                    Tournament = tournament
                };
                
                // create the pairings for the first round we know this list has even size
                while (competitors.Count() != 0)
                {
                    Competitor comp1 = competitors.First();
                    Competitor comp2 = competitors.Last();
                    competitors.RemoveAt(0);
                    competitors.RemoveAt(competitors.Count() - 1);
                    Pairing newPairing = new Pairing
                    {
                        CompetitorId1 = comp1.Id,
                        CompetitorId2 = comp2.Id,
                        Round = newRound,
                        Result = null,
                    };
                    context.Pairings.Add(newPairing);
                }
                context.Rounds.Add(newRound);
            }
            else
            {
                // Winners from the previous round get to advance
                List<Competitor> winners = lastRound.Pairings
                    .Select(p => p.Result == p.CompetitorId1 ? p.CompetitorId1Navigation : p.CompetitorId2Navigation)
                    .ToList();

                // if there is only 1 person left the tournament over
                if (winners.Count() == 1)
                {
                    tournament.OnGoing = false;
                    context.SaveChanges();
                    return;
                }

                // TODO: create pairings as per rules rather than making things up
                Round newRound = new Round
                {
                    RoundNumber = lastRound.RoundNumber + 1,
                    Tournament = tournament
                };
                while (winners.Count() != 0)
                {
                    Competitor comp1 = winners.First();
                    Competitor comp2 = winners.Last();
                    winners.RemoveAt(0);
                    winners.RemoveAt(winners.Count() - 1);
                    Pairing newPairing = new Pairing
                    {
                        CompetitorId1 = comp1.Id,
                        CompetitorId2 = comp2.Id,
                        Round = newRound,
                        Result = null,
                    };
                    context.Pairings.Add(newPairing);
                }
                context.Rounds.Add(newRound);
            }
            context.SaveChanges();
        }
        private static void CreateDoubleEliminationRound(TournamentMasterDBContext context, Tournament tournament)
        {
            // Get latest round if there is one else null
            Round currentRound = context.Rounds.Where(r => r.TournamentId == tournament.Id)
                .OrderByDescending(r => r.RoundNumber)
                .FirstOrDefault();
        }
        private static void CreateRoundRobinRound(TournamentMasterDBContext context, Tournament tournament)
        {
            // Get latest round if there is one else null
            Round currentRound = context.Rounds.Where(r => r.TournamentId == tournament.Id)
                .OrderByDescending(r => r.RoundNumber)
                .FirstOrDefault();
        }
        private static void CreateSwissRound(TournamentMasterDBContext context, Tournament tournament)
        {
            // Get latest round if there is one else null
            Round currentRound = context.Rounds.Where(r => r.TournamentId == tournament.Id)
                .OrderByDescending(r => r.RoundNumber)
                .FirstOrDefault();
        }
    }
}
