using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TournamentMasterAPI.Models;
using System.Security.Claims;

namespace TournamentMasterAPI.Controllers
{
    [Produces("application/json")]
    [Route("api/Competitors")]
    public class CompetitorsController : Controller
    {
        private readonly TournamentMasterDBContext _context;

        public CompetitorsController(TournamentMasterDBContext context)
        {
            _context = context;
        }

        // GET: api/Competitors
        [HttpGet]
        public IEnumerable<Competitors> GetCompetitors()
        {
            return _context.Competitors;
        }

        // GET: api/Competitors/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetCompetitors([FromRoute] int id)
        {
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
        public async Task<IActionResult> PutCompetitors([FromRoute] int id, [FromBody] Competitors competitors)
        {
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
                if (!CompetitorsExists(id))
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
        public async Task<IActionResult> PostCompetitors([FromBody] Competitors competitors)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Competitors.Add(competitors);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCompetitors", new { id = competitors.Id }, competitors);
        }

        // DELETE: api/Competitors/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCompetitors([FromRoute] int id)
        {
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

        private bool CompetitorsExists(int id)
        {
            return _context.Competitors.Any(e => e.Id == id);
        }
    }
}