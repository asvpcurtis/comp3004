using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TournamentMasterAPI.Models;
using System.Security.Claims;
using Microsoft.AspNetCore.Authorization;

namespace TournamentMasterAPI.Controllers
{
    [Produces("application/json")]
    [Route("api/Organizations")]
    [Authorize]
    public class OrganizationsController : Controller
    {
        private readonly TournamentMasterDBContext _context;
        public OrganizationsController(TournamentMasterDBContext context)
        {
            _context = context;
        }   

        // GET: api/Organizations
        [HttpGet]
        public IEnumerable<Organization> GetOrganizations()
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            return Shared.UserOrganizations(userAccount, _context);
        }

        // GET: api/Organizations/5
        [HttpGet("{id:int}")]
        public async Task<IActionResult> GetOrganizations([FromRoute] int id)
        {
            // check if user has permission for this organization
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserOrganizations(userAccount, _context).Any(o => o.Id == id))
            {
                return Unauthorized();
            }

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var organizations = await _context.Organizations.SingleOrDefaultAsync(m => m.Id == id);

            if (organizations == null)
            {
                return NotFound();
            }
            
            return Ok(organizations);
        }

        // PUT: api/Organizations/5
        [HttpPut("{id:int}")]
        public async Task<IActionResult> PutOrganizations([FromRoute] int id, [FromBody] Organization organizations)
        {
            // check if user has permission for this organization
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserOrganizations(userAccount, _context).Any(o => o.Id == id))
            {
                return Unauthorized();
            }

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != organizations.Id)
            {
                return BadRequest();
            }
            Organization entity = _context.Organizations.Find(organizations.Id);
            if (entity == null)
            {
                return NotFound();
            }
            _context.Entry(entity).CurrentValues.SetValues(organizations);
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!OrganizationExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Organizations
        [HttpPost]
        public async Task<IActionResult> PostOrganizations([FromBody] Organization organizations)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            userAccount.AccountOrganization.Add(new AccountOrganization {
                Organization = organizations
            });

            await _context.SaveChangesAsync();
            return CreatedAtAction("GetOrganizations", new { id = organizations.Id }, organizations);
        }

        // DELETE: api/Organizations/5
        [HttpDelete("{id:int}")]
        public async Task<IActionResult> DeleteOrganizations([FromRoute] int id)
        {
            // check if user has permission for this organization
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserOrganizations(userAccount, _context).Any(o => o.Id == id))
            {
                return Unauthorized();
            }

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var organizations = await _context.Organizations.SingleOrDefaultAsync(m => m.Id == id);
            if (organizations == null)
            {
                return NotFound();
            }

            _context.AccountOrganization.RemoveRange(_context.AccountOrganization
                .Where(ao => ao.OrganizationId == organizations.Id && ao.AccountId == userAccount.Id));
            
            await _context.SaveChangesAsync();

            return Ok(organizations);
        }

        private bool OrganizationExists(int id)
        {
            return _context.Organizations.Any(e => e.Id == id);
        }
    }
}