using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class CompetitorTournament
    {
        public int CompetitorsId { get; set; }
        public int TournamentsId { get; set; }

        public Competitors Competitors { get; set; }
        public Tournaments Tournaments { get; set; }
    }
}
