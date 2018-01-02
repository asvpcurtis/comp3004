using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TournamentMasterAPI.Models
{
    public class TournamentParameters
    {
        public Tournament Tournament { get; set; }
        public Competitor[] Competitors { get; set; }
    }
}
