using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TournamentMasterAPI.Models;
using Microsoft.AspNetCore.Authorization;

namespace TournamentMasterAPI.Controllers
{
    [Produces("application/json")]
    [Route("api/OrganizationAccounts")]
    [Authorize]
    public class OrganizationAccountsController : Controller
    {
        private readonly TournamentMasterDBContext _context;

        public OrganizationAccountsController(TournamentMasterDBContext context)
        {
            _context = context;
        }

        // GET: api/OrganizationAccounts
        [HttpGet("{oid}")]
        public IActionResult GetAccountOrganization([FromRoute] int oid)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserOrganizations(userAccount, _context).Any(o => o.Id == oid))
            {
                return Unauthorized();
            }
            return Ok(_context.AccountOrganization.Where(ao => ao.OrganizationId == oid));
        }

        // POST: api/OrganizationAccounts
        [HttpPost]
        public async Task<IActionResult> PostAccountOrganization([FromBody] AccountOrganization accountOrganization)
        {
            // check if user has permission for this organization
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserOrganizations(userAccount, _context).Any(o => o.Id == accountOrganization.OrganizationId))
            {
                return Unauthorized();
            }

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.AccountOrganization.Add(accountOrganization);
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (AccountOrganizationExists(accountOrganization.AccountId))
                {
                    return new StatusCodeResult(StatusCodes.Status409Conflict);
                }
                else
                {
                    throw;
                }
            }

            return Ok(accountOrganization);
        }
        

        private bool AccountOrganizationExists(int id)
        {
            return _context.AccountOrganization.Any(e => e.AccountId == id);
        }
    }
}