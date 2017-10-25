using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TournamentMasterAPI.Models;

namespace TournamentMasterAPI.Controllers
{
    [Produces("application/json")]
    [Route("api/Organizations")]
    public class OrganizationsController : Controller
    {
        private readonly TournamentMasterDBContext _context;

        public OrganizationsController(TournamentMasterDBContext context)
        {
            _context = context;
        }

        // GET: api/Organizations
        [HttpGet]
        public IEnumerable<Organizations> GetOrganizations()
        {
            return _context.Organizations;
        }

        // GET: api/Organizations/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetOrganizations([FromRoute] int id)
        {
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
        [HttpPut("{id}")]
        public async Task<IActionResult> PutOrganizations([FromRoute] int id, [FromBody] Organizations organizations)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != organizations.Id)
            {
                return BadRequest();
            }

            _context.Entry(organizations).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!OrganizationsExists(id))
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
        public async Task<IActionResult> PostOrganizations([FromBody] Organizations organizations)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Organizations.Add(organizations);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetOrganizations", new { id = organizations.Id }, organizations);
        }

        // DELETE: api/Organizations/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteOrganizations([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var organizations = await _context.Organizations.SingleOrDefaultAsync(m => m.Id == id);
            if (organizations == null)
            {
                return NotFound();
            }

            _context.Organizations.Remove(organizations);
            await _context.SaveChangesAsync();

            return Ok(organizations);
        }

        private bool OrganizationsExists(int id)
        {
            return _context.Organizations.Any(e => e.Id == id);
        }
    }
}