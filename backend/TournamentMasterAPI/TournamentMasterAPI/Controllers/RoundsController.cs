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
    [Route("api/Rounds")]
    public class RoundsController : Controller
    {
        private readonly TournamentMasterDBContext _context;

        public RoundsController(TournamentMasterDBContext context)
        {
            _context = context;
        }

        // GET: api/Rounds
        [HttpGet]
        public IEnumerable<Round> GetRounds()
        {
            return _context.Rounds;
        }

        // GET: api/Rounds/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetRound([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var round = await _context.Rounds.SingleOrDefaultAsync(m => m.Id == id);

            if (round == null)
            {
                return NotFound();
            }

            return Ok(round);
        }

        private bool RoundExists(int id)
        {
            return _context.Rounds.Any(e => e.Id == id);
        }
    }
}