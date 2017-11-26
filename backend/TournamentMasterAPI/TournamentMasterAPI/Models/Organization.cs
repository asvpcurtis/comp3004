using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class Organization
    {
        public Organization()
        {
            AccountOrganization = new HashSet<AccountOrganization>();
            Competitors = new HashSet<Competitor>();
            Tournaments = new HashSet<Tournament>();
        }

        public int Id { get; set; }
        public string Name { get; set; }

        [JsonIgnore]
        public ICollection<AccountOrganization> AccountOrganization { get; set; }
        [JsonIgnore]
        public ICollection<Competitor> Competitors { get; set; }
        [JsonIgnore]
        public ICollection<Tournament> Tournaments { get; set; }
    }
}
