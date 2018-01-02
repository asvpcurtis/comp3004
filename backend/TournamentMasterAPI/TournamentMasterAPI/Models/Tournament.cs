using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class Tournament
    {
        public Tournament()
        {
            CompetitorTournament = new HashSet<CompetitorTournament>();
            Rounds = new HashSet<Round>();
        }

        public int Id { get; set; }
        public int OrganizationId { get; set; }
        public DateTime StartDate { get; set; }
        public int Format { get; set; }
        public bool OnGoing { get; set; }
        public string Name { get; set; }
        [JsonIgnore]
        public Organization Organization { get; set; }
        [JsonIgnore]
        public ICollection<CompetitorTournament> CompetitorTournament { get; set; }
        [JsonIgnore]
        public ICollection<Round> Rounds { get; set; }
    }
}
