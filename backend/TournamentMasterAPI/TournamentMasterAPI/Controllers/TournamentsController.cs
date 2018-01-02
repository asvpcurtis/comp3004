using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TournamentMasterAPI.Models;
using TournamentMasterAPI.Builders;
using Microsoft.AspNetCore.Authorization;

namespace TournamentMasterAPI.Controllers
{
    [Produces("application/json")]
    [Route("api/Tournaments")]
    [Authorize]
    public class TournamentsController : Controller
    {
        private readonly TournamentMasterDBContext _context;

        public TournamentsController(TournamentMasterDBContext context)
        {
            _context = context;
        }

        // GET: api/Tournaments?organization=5
        [HttpGet("{organization?}")]
        public IEnumerable<Tournament> GetTournaments([FromQuery] int? organization = null)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            IEnumerable<Tournament> userTournaments = Shared.UserTournaments(userAccount, _context);
            if (organization != null)
            {
                userTournaments = userTournaments.Where(t => t.OrganizationId == organization);
            }
            List<Tournament> trimmedUserTournaments = userTournaments.ToList();
            foreach (Tournament t in trimmedUserTournaments)
            {
                t.Name = t.Name.TrimEnd(' ');
            }
            return userTournaments;
        }

        // GET: api/Tournaments/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetTournaments([FromRoute] int id)
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserTournaments(userAccount, _context).Any(t => t.Id == id))
            {
                return Unauthorized();
            }
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var tournaments = await _context.Tournaments.SingleOrDefaultAsync(m => m.Id == id);

            if (tournaments == null)
            {
                return NotFound();
            }
            tournaments.Name = tournaments.Name.TrimEnd(' ');
            return Ok(tournaments);
        }

        // POST: api/Tournaments
        //[HttpPost("{seed}")]
        [HttpPost]
        public async Task<IActionResult> PostTournaments([FromBody] TournamentParameters tparams, [FromQuery] string seed = "manual")
        {
            // this will disable warning
            // await Task.Run(() => { });
            Account userAccount = Shared.GetUserAccount(User, _context);
            if (!Shared.UserOrganizations(userAccount,_context).Any(o => o.Id == tparams.Tournament.OrganizationId))
            {
                return Unauthorized();
            }
            foreach(Competitor comp in tparams.Competitors)
            {
                if (comp.OrganizationId != tparams.Tournament.OrganizationId)
                {
                    return BadRequest();
                }
                if (!_context.Competitors.Any(c => c.Id == comp.Id))
                {
                    return BadRequest();
                }
            }
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            TournamentBuilder.InititializeTournament(_context, tparams.Competitors, tparams.Tournament, seed);
            _context.SaveChanges();
            return CreatedAtAction("GetTournaments", new { id = tparams.Tournament.Id }, tparams.Tournament);
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