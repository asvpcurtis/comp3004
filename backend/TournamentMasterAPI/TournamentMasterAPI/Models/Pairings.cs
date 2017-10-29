using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class Pairings
    {
        public int Id { get; set; }
        public int RoundId { get; set; }
        public int Result { get; set; }
        public int CompetitorId1 { get; set; }
        public int CompetitorId2 { get; set; }

        [JsonIgnore]
        public Competitors CompetitorId1Navigation { get; set; }
        [JsonIgnore]
        public Competitors CompetitorId2Navigation { get; set; }
        [JsonIgnore]
        public Rounds Round { get; set; }
    }
}
