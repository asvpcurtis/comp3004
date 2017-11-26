using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TournamentMasterAPI.Models;
namespace TournamentMasterAPI.Builders
{
    public static class RatingCalculator
    {
        private const double K_FACTOR = 32;
        public const int INIITIAL_RATING = 1000;
        public static void UpdateRatings(TournamentMasterDBContext context, Pairing game)
        {
            if (game.Result == null || (game.Result != game.CompetitorId1 && game.Result != game.CompetitorId2))
            {
                throw new ArgumentException("");
            }

            double comp1RealScore;
            double comp2RealScore;
            if (game.Result == game.CompetitorId1)
            {
                comp1RealScore = 1;
                comp2RealScore = 0;
            }
            else
            {
                comp1RealScore = 0;
                comp2RealScore = 1;
            }

            Competitor comp1 = context.Competitors.Where(c => c.Id == game.CompetitorId1).First();
            Competitor comp2 = context.Competitors.Where(c => c.Id == game.CompetitorId2).First();
            double comp1Rating = comp1.Rating;
            double comp2Rating = comp2.Rating;

            double comp1ExpectedScore = 1 / (1 + Math.Pow(10, (comp2Rating - comp1Rating) / 400));
            double comp2ExpectedScore = 1 - comp1ExpectedScore;

            double comp1NewRating = comp1Rating + (K_FACTOR * (comp1RealScore - comp1ExpectedScore));
            double comp2NewRating = comp2Rating + (K_FACTOR * (comp2RealScore - comp2ExpectedScore));
            comp1.Rating = (int)comp1NewRating;
            comp2.Rating = (int)comp2NewRating;
            context.SaveChanges();
        }
    }
}
