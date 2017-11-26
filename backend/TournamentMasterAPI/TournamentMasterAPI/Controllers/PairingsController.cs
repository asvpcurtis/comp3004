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
    [Route("api/Pairings")]
    public class PairingsController : Controller
    {
        private readonly TournamentMasterDBContext _context;

        public PairingsController(TournamentMasterDBContext context)
        {
            _context = context;
        }

        // GET: api/Pairings
        [HttpGet]
        public IEnumerable<Pairing> GetPairings()
        {
            return _context.Pairings;
        }

        // GET: api/Pairings/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetPairing([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var pairing = await _context.Pairings.SingleOrDefaultAsync(m => m.Id == id);

            if (pairing == null)
            {
                return NotFound();
            }

            return Ok(pairing);
        }

        // PUT: api/Pairings/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutPairing([FromRoute] int id, [FromBody] Pairing pairing)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != pairing.Id)
            {
                return BadRequest();
            }

            _context.Entry(pairing).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PairingExists(id))
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

        // POST: api/Pairings
        [HttpPost]
        public async Task<IActionResult> PostPairing([FromBody] Pairing pairing)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Pairings.Add(pairing);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPairing", new { id = pairing.Id }, pairing);
        }

        // DELETE: api/Pairings/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeletePairing([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var pairing = await _context.Pairings.SingleOrDefaultAsync(m => m.Id == id);
            if (pairing == null)
            {
                return NotFound();
            }

            _context.Pairings.Remove(pairing);
            await _context.SaveChangesAsync();

            return Ok(pairing);
        }

        private bool PairingExists(int id)
        {
            return _context.Pairings.Any(e => e.Id == id);
        }
    }
}