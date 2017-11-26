using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class AccountOrganization
    {
        public int AccountId { get; set; }
        public int OrganizationId { get; set; }
        [JsonIgnore]
        public Account Account { get; set; }
        [JsonIgnore]
        public Organization Organization { get; set; }
    }
}
