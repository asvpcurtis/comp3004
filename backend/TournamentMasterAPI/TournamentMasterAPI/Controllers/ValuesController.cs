using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using System.Security.Claims;

namespace TournamentMasterAPI.Controllers
{
    [Route("api/[controller]")]
    public class ValuesController : Controller
    {
        // GET api/values
        [HttpGet]
        public IEnumerable<string> Get()
        {
            //[
            //    "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier:4ee88d13-9955-4eca-a820-7ee39dc18141",
            //    "email_verified:false",
            //    "iss:https://cognito-idp.us-east-2.amazonaws.com/us-east-2_79EXSEFbV",
            //    "phone_number_verified:false",
            //    "cognito:username:Test",
            //    "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname:Testy",
            //    "aud:lfj0sjp1ec0khsbrpm7jdc1s7",
            //    "token_use:id",
            //    "auth_time:1508987645",
            //    "phone_number:+16132539275",
            //    "exp:1508991245",
            //    "iat:1508987646",
            //    "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname:Testy",
            //    "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress:michaeljsouter@gmail.com"
            //]
            string aud = User.FindFirst("aud")?.Value;
            string surname = User.FindFirst(ClaimTypes.Surname)?.Value;
            string givenname = User.FindFirst(ClaimTypes.GivenName)?.Value;
            string sub = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            string email = User.FindFirst(ClaimTypes.Email)?.Value;
            User.Claims.Select(c => c.Type + ":" + c.Value);
            return User.Claims.Select(c =>c.Type + ":" + c.Value);
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            string sub = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            return sub;
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody]string value)
        {
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
