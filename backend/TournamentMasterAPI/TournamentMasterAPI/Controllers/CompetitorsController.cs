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
using System.IO;

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
        [HttpGet("{organization?}")]
        public IEnumerable<Competitor> GetOrganizationCompetitors([FromQuery] int? organization = null)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            IEnumerable<Competitor> userCompetitors = Shared.UserCompetitors(userAccount, _context);
            if (organization != null)
            {
                userCompetitors = userCompetitors.Where(c => c.OrganizationId == organization);
            }
            /*
            using (StreamWriter file = System.IO.File.AppendText(@"C:\Users\Curtis Barlow-Wilkes\Desktop\0log.txt"))
            {
                foreach (Competitor c in userCompetitors)
                {
                    file.WriteLine($"{c.Id}, {c.FirstName}");
                }
            }
            */
            return userCompetitors;
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

        // POST: api/Competitors
        [HttpPost]
        public async Task<IActionResult> PostCompetitors([FromBody] Competitor competitor)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var organizations = Shared.UserOrganizations(userAccount, _context)
                .SingleOrDefault(o => o.Id == competitor.OrganizationId);
            if (organizations != null)
            {
                organizations.Competitors.Add(competitor);
            }
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCompetitors", new { id = competitor.Id }, competitor);
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