using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class AccountOrganization
    {
        public int AccountsId { get; set; }
        public int OrganizationsId { get; set; }

        [JsonIgnore]
        public Accounts Accounts { get; set; }
        [JsonIgnore]
        public Organizations Organizations { get; set; }
    }
}
