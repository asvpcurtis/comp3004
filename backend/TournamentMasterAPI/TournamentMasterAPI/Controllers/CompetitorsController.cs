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
    [Route("api/Competitors")]
    [Authorize]
    public class CompetitorsController : Controller
    {
        private readonly TournamentMasterDBContext _context;
        public CompetitorsController(TournamentMasterDBContext context)
        {
            _context = context;
        }

        // GET: api/Competitors?organization=5
        [Route("api/Competitors")]
        [HttpGet("{organization?}")]
        public IEnumerable<Competitor> GetOrganizationCompetitors([FromQuery] int? oid = null)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            IEnumerable<Competitor> userCompetitors  = Shared.UserCompetitors(userAccount, _context);
            if (oid == null)
            {
                return userCompetitors;
            }
            return userCompetitors.Where(c => c.OrganizationId == oid);
        }

        // GET: api/Competitors/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetCompetitors([FromRoute] int id)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserCompetitors(userAccount, _context).Any(c => c.Id == id))
            {
                return Unauthorized();
            }

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var competitors = await _context.Competitors.SingleOrDefaultAsync(m => m.Id == id);

            if (competitors == null)
            {
                return NotFound();
            }

            return Ok(competitors);
        }

        // PUT: api/Competitors/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutCompetitors([FromRoute] int id, [FromBody] Competitor competitors)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserCompetitors(userAccount, _context).Any(c => c.Id == id))
            {
                return Unauthorized();
            }
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != competitors.Id)
            {
                return BadRequest();
            }

            _context.Entry(competitors).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CompetitorExists(id))
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

        // POST: api/Organization/5/Competitors
        [Route("api/Organization/{oid:int}/Competitors")]
        [HttpPost]
        public async Task<IActionResult> PostCompetitors([FromRoute] int oid, [FromBody] Competitor competitors)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var organizations = Shared.UserOrganizations(userAccount, _context).SingleOrDefault(o => o.Id == oid);
            if (organizations != null)
            {
                //organizations.Competitors.Add(competitors);
            }
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCompetitors", new { id = competitors.Id }, competitors);
        }

        // DELETE: api/Competitors/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCompetitors([FromRoute] int id)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserCompetitors(userAccount, _context).Any(c => c.Id == id))
            {
                return Unauthorized();
            }
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var competitors = await _context.Competitors.SingleOrDefaultAsync(m => m.Id == id);
            if (competitors == null)
            {
                return NotFound();
            }

            _context.Competitors.Remove(competitors);
            await _context.SaveChangesAsync();

            return Ok(competitors);
        }

        private bool CompetitorExists(int id)
        {
            return _context.Competitors.Any(e => e.Id == id);
        }
    }
}