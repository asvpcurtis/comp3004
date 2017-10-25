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

        public Competitors CompetitorId1Navigation { get; set; }
        public Competitors CompetitorId2Navigation { get; set; }
        public Rounds Round { get; set; }
    }
}
