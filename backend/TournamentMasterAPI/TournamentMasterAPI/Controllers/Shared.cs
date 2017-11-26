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
        public static Account GetUserAccount(ClaimsPrincipal user, TournamentMasterDBContext context)
        {
            string sub = user.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            Guid userId = new Guid(sub);
            // if account doesn't already exist create one!
            if (!context.Accounts.Any(e => e.AwsCognitoId == userId))
            {
                Account acc = new Account
                {
                    AwsCognitoId = userId
                };
                context.Accounts.Add(acc);
                context.SaveChanges();
            }
            return context.Accounts.FirstOrDefault(acc => acc.AwsCognitoId == userId);
        }

        public static IEnumerable<Organization> UserOrganizations(Account userAccount, TournamentMasterDBContext context)
        {
            return context.AccountOrganization
                .Where(ao => ao.AccountId == userAccount.Id)
                .Select(ao => ao.Organization);
        }

        public static IEnumerable<Competitor> UserCompetitors(Account userAccount, TournamentMasterDBContext context)
        {
            return UserOrganizations(userAccount, context)
                .SelectMany(o => o.Competitors);
        }

        public static IEnumerable<Tournament> UserTournaments(Account userAccount, int tournamentId, TournamentMasterDBContext context)
        {
            return UserOrganizations(userAccount, context)
                .SelectMany(o => o.Tournaments);
        }

    }
}
