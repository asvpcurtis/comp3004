using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using TournamentMasterAPI.Models;
using System.Data.Entity;
using System.Net.Mail;

namespace TournamentMasterAPI.Controllers
{
    public class AccountController : ApiController
    {
        private TournamentMasterDataAccessContainer db = new TournamentMasterDataAccessContainer();

        [Route("api/account/register")]
        [HttpPost]
        public string register(Account newacc)
        {
            if (db.Accounts.Any(e => e.Email == newacc.Email) 
                || newacc.Email.Length > 254)
            {
                return "Email is either invalid or already exists.";
            }
            else if (newacc.Password.Length > 8 && newacc.Password.Length < 16
                && newacc.Password.Any(e => char.IsDigit(e)) 
                && newacc.Password.Any(e => char.IsUpper(e)) 
                && newacc.Password.Any(e => char.IsLower(e))
                && newacc.Password.Any(e => char.IsSymbol(e)))
            {

                return "Account Created, confirmation email sent.";
            }
            else
            {
                return "Passwords must be between 8-16 characters long with atleast one digit, uppercase, lowercase and symbol.";
            }
            
        }
        [Route("api/account/{userid}/confirm")]
        [HttpGet]
        public void confirm(int userid, [FromUri]string code)
        {
            Request.CreateResponse(200);
        }

        [Route("api/account/login")]
        [HttpPost]
        public bool login()
        {
            //return model.email + ":" + model.password;
            return true;
        }

        [Route("api/account/close")]
        [HttpDelete]
        public bool closeAccount()
        {
            //return model.email + ":" + model.password;
            return true;
        }

    }
    
}