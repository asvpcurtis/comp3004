using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TournamentMasterAPI.Models;
using TournamentMasterAPI.Builders;

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
        [HttpGet("{round?}")]
        public IEnumerable<Pairing> GetPairings([FromQuery] int? round = null)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            IEnumerable<Pairing> userPairings = Shared.UserPairings(userAccount, _context);
            if (round != null)
            {
                userPairings = userPairings.Where(p => p.RoundId == round);
            }
            return userPairings;
        }

        // GET: api/Pairings/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetPairing([FromRoute] int id)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserPairings(userAccount, _context).Any(p => p.Id == id))
            {
                return Unauthorized();
            }
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
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserPairings(userAccount, _context).Any(p => p.Id == id))
            {
                return Unauthorized();
            }

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != pairing.Id)
            {
                return BadRequest();
            }


            Pairing entity = _context.Pairings.Find(pairing.Id);
            if (entity == null)
            {
                return NotFound();
            }

            // making sure only the result can be updated 
            pairing.CompetitorId1 = entity.CompetitorId1;
            pairing.CompetitorId2 = entity.CompetitorId2;
            pairing.RoundId = entity.RoundId;

            if (pairing.Result != pairing.CompetitorId1 || pairing.Result != pairing.CompetitorId2)
            {
                BadRequest();
            }
                
            _context.Entry(entity).CurrentValues.SetValues(pairing);
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

            // Get the tournament that the pairing belongs to
            Round round = _context.Rounds.Find(pairing.RoundId);
            Tournament tournament = _context.Tournaments.Find(round.TournamentId);
            TournamentBuilder.UpdateTournament(_context, tournament);

            return NoContent();
        }

        private bool PairingExists(int id)
        {
            return _context.Pairings.Any(e => e.Id == id);
        }
    }
}