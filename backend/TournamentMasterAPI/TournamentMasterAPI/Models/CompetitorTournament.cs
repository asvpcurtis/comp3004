using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class CompetitorTournament
    {
        public int CompetitorsId { get; set; }
        public int TournamentsId { get; set; }

        [JsonIgnore]
        public Competitors Competitors { get; set; }
        [JsonIgnore]
        public Tournaments Tournaments { get; set; }
    }
}
