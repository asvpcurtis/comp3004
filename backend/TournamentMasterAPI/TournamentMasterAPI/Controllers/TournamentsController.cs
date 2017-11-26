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
    [Route("api/Tournaments")]
    public class TournamentsController : Controller
    {
        private readonly TournamentMasterDBContext _context;

        public TournamentsController(TournamentMasterDBContext context)
        {
            _context = context;
        }

        // GET: api/Tournaments
        [HttpGet]
        public IEnumerable<Tournament> GetTournaments()
        {
            return _context.Tournaments;
        }

        // GET: api/Tournaments/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetTournaments([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var tournaments = await _context.Tournaments.SingleOrDefaultAsync(m => m.Id == id);

            if (tournaments == null)
            {
                return NotFound();
            }

            return Ok(tournaments);
        }

        // PUT: api/Tournaments/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutTournaments([FromRoute] int id, [FromBody] Tournament tournaments)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != tournaments.Id)
            {
                return BadRequest();
            }

            _context.Entry(tournaments).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TournamentExists(id))
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

        // POST: api/Tournaments
        [HttpPost]
        public async Task<IActionResult> PostTournaments([FromBody] Tournament tournaments)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Tournaments.Add(tournaments);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetTournaments", new { id = tournaments.Id }, tournaments);
        }

        // DELETE: api/Tournaments/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTournaments([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var tournaments = await _context.Tournaments.SingleOrDefaultAsync(m => m.Id == id);
            if (tournaments == null)
            {
                return NotFound();
            }

            _context.Tournaments.Remove(tournaments);
            await _context.SaveChangesAsync();

            return Ok(tournaments);
        }

        private bool TournamentExists(int id)
        {
            return _context.Tournaments.Any(e => e.Id == id);
        }
    }
}