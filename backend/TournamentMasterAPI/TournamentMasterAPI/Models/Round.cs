using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class Round
    {
        public Round()
        {
            Pairings = new HashSet<Pairing>();
        }

        public int Id { get; set; }
        public int TournamentId { get; set; }
        public int RoundNumber { get; set; }
        [JsonIgnore]
        public Tournament Tournament { get; set; }
        [JsonIgnore]
        public ICollection<Pairing> Pairings { get; set; }
    }
}
