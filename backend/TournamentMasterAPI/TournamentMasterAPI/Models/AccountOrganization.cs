using System;
using System.Collections.Generic;

namespace TournamentMasterAPI.Models
{
    public partial class AccountOrganization
    {
        public int AccountsId { get; set; }
        public int OrganizationsId { get; set; }

        public Accounts Accounts { get; set; }
        public Organizations Organizations { get; set; }
    }
}
