using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class CompetitorTournament
    {
        public int CompetitorId { get; set; }
        public int TournamentId { get; set; }
        public int Seed { get; set; }
        [JsonIgnore]
        public Competitor Competitor { get; set; }
        [JsonIgnore]
        public Tournament Tournament { get; set; }
    }
}
