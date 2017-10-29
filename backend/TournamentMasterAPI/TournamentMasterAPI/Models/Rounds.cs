using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class Rounds
    {
        public Rounds()
        {
            Pairings = new HashSet<Pairings>();
        }

        public int Id { get; set; }
        public int TournamentId { get; set; }
        public int RoundNumber { get; set; }

        [JsonIgnore]
        public Tournaments Tournament { get; set; }
        [JsonIgnore]
        public ICollection<Pairings> Pairings { get; set; }
    }
}
