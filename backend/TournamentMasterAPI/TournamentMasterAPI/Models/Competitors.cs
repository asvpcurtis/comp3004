using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class Competitors
    {
        public Competitors()
        {
            CompetitorTournament = new HashSet<CompetitorTournament>();
            PairingsCompetitorId1Navigation = new HashSet<Pairings>();
            PairingsCompetitorId2Navigation = new HashSet<Pairings>();
        }

        public int Id { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public int Rating { get; set; }
        public int OrganizationId { get; set; }
        public string Gender { get; set; }

        public Organizations Organization { get; set; }
        public ICollection<CompetitorTournament> CompetitorTournament { get; set; }
        public ICollection<Pairings> PairingsCompetitorId1Navigation { get; set; }
        public ICollection<Pairings> PairingsCompetitorId2Navigation { get; set; }
    }
}
