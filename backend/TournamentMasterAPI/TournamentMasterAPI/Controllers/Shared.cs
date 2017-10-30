using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using TournamentMasterAPI.Models;

namespace TournamentMasterAPI.Controllers
{
    public static class Shared
    {
        public static Accounts GetUserAccount(ClaimsPrincipal user, TournamentMasterDBContext context)
        {
            string sub = user.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            Guid userId = new Guid(sub);
            // if account doesn't already exist create one!
            if (!context.Accounts.Any(e => e.AwsCognitoId == userId))
            {
                Accounts acc = new Accounts
                {
                    AwsCognitoId = userId
                };
                context.Accounts.Add(acc);
            }
            return context.Accounts.FirstOrDefault(acc => acc.AwsCognitoId == userId);
        }

        public static IEnumerable<Organizations> UserOrganizations(Accounts userAccount, TournamentMasterDBContext context)
        {
            return context.AccountOrganization
                .Where(ao => ao.AccountsId == userAccount.Id)
                .Select(ao => ao.Organizations);
        }

        public static IEnumerable<Competitors> UserCompetitors(Accounts userAccount, TournamentMasterDBContext context)
        {
            return UserOrganizations(userAccount, context)
                .SelectMany(o => o.Competitors);
        }

        public static IEnumerable<Tournaments> UserTournaments(Accounts userAccount, int tournamentId, TournamentMasterDBContext context)
        {
            return UserOrganizations(userAccount, context)
                .SelectMany(o => o.Tournaments);
        }

    }
}
