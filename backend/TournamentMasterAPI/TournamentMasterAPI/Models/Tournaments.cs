using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class Tournaments
    {
        public Tournaments()
        {
            CompetitorTournament = new HashSet<CompetitorTournament>();
            Rounds = new HashSet<Rounds>();
        }

        public int Id { get; set; }
        public int OrganizationId { get; set; }
        public DateTime StartDate { get; set; }
        public int Format { get; set; }
        public bool OnGoing { get; set; }

        public Organizations Organization { get; set; }
        public ICollection<CompetitorTournament> CompetitorTournament { get; set; }
        public ICollection<Rounds> Rounds { get; set; }
    }
}
