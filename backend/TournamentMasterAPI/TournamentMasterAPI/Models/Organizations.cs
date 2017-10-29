using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class Organizations
    {
        public Organizations()
        {
            AccountOrganization = new HashSet<AccountOrganization>();
            Competitors = new HashSet<Competitors>();
            Tournaments = new HashSet<Tournaments>();
        }

        public int Id { get; set; }
        public string Name { get; set; }

        [JsonIgnore]
        public ICollection<AccountOrganization> AccountOrganization { get; set; }
        [JsonIgnore]
        public ICollection<Competitors> Competitors { get; set; }
        [JsonIgnore]
        public ICollection<Tournaments> Tournaments { get; set; }
    }
}
