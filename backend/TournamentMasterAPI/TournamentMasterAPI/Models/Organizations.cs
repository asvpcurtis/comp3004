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

        public ICollection<AccountOrganization> AccountOrganization { get; set; }
        public ICollection<Competitors> Competitors { get; set; }
        public ICollection<Tournaments> Tournaments { get; set; }
    }
}
