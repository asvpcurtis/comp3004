using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using TournamentMasterAPI.Models;

namespace TournamentMasterAPI.Controllers
{
    public static class Shared
    {
        public static Account GetUserAccount(ClaimsPrincipal user, TournamentMasterDBContext context)
        {
            string sub = user.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            Guid userId = new Guid(sub);
            // if account doesn't already exist create one!
            if (!context.Accounts.Any(e => e.AwsCognitoId == userId))
            {
                Account acc = new Account
                {
                    AwsCognitoId = userId
                };
                context.Accounts.Add(acc);
                context.SaveChanges();
            }
            return context.Accounts.FirstOrDefault(acc => acc.AwsCognitoId == userId);
        }

        public static IEnumerable<Organization> UserOrganizations(Account userAccount, TournamentMasterDBContext context)
        {
            return context.AccountOrganization
                .Where(ao => ao.AccountId == userAccount.Id)
                .Select(ao => ao.Organization).ToList();
        }

        public static IEnumerable<Competitor> UserCompetitors(Account userAccount, TournamentMasterDBContext context)
        {

            IEnumerable<Organization> organizations = UserOrganizations(userAccount, context);
            IEnumerable<Competitor> competitors = context.Competitors
                .Where(c => organizations
                .Any(o => o.Id == c.OrganizationId));
            return competitors.ToList();
        }

        public static IEnumerable<Tournament> UserTournaments(Account userAccount, TournamentMasterDBContext context)
        {
            IEnumerable<Organization> organizations = UserOrganizations(userAccount, context);
            IEnumerable<Tournament> tournaments = context.Tournaments
                .Where(t => organizations
                .Any(o => o.Id == t.OrganizationId));
            return tournaments.ToList();
        }

        public static IEnumerable<Round> UserRounds(Account userAccount, TournamentMasterDBContext context)
        {
            IEnumerable <Tournament> tournaments = UserTournaments(userAccount, context);
            IEnumerable<Round> rounds = context.Rounds
                .Where(r => tournaments
                .Any(t => t.Id == r.TournamentId));
            return rounds.ToList();
        }

        public static IEnumerable<Pairing> UserPairings(Account userAccount, TournamentMasterDBContext context)
        {
            IEnumerable<Round> rounds = UserRounds(userAccount, context);
            IEnumerable<Pairing> pairings = context.Pairings
                .Where(p => rounds
                .Any(r => r.Id == p.RoundId));
            return pairings.ToList();
        }

    }
}
