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

        public Tournaments Tournament { get; set; }
        public ICollection<Pairings> Pairings { get; set; }
    }
}
