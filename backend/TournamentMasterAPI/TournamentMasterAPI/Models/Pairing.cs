using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class Pairing
    {
        public int Id { get; set; }
        public int RoundId { get; set; }
        public int? Result { get; set; }
        public int CompetitorId1 { get; set; }
        public int CompetitorId2 { get; set; }
        [JsonIgnore]
        public Competitor CompetitorId1Navigation { get; set; }
        [JsonIgnore]
        public Competitor CompetitorId2Navigation { get; set; }
        [JsonIgnore]
        public Round Round { get; set; }
    }
}
