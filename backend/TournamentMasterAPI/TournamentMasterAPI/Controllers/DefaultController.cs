using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using TournamentMasterAPI.Models;

namespace TournamentMasterAPI.Controllers
{
    [Authorize]
    [Route("api")]
    public class DefaultController : Controller
    {
        private readonly TournamentMasterDBContext _context;
        public DefaultController(TournamentMasterDBContext context)
        {
            _context = context;
        }

        public IActionResult Index()
        {
            Account userAccount = Shared.GetUserAccount(User, _context);
            return Ok(userAccount.Id);
        }
    }
}