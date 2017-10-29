using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace TournamentMasterAPI.Models
{
    public partial class TournamentMasterDBContext : DbContext
    {
        public virtual DbSet<AccountOrganization> AccountOrganization { get; set; }
        public virtual DbSet<Accounts> Accounts { get; set; }
        public virtual DbSet<Competitors> Competitors { get; set; }
        public virtual DbSet<CompetitorTournament> CompetitorTournament { get; set; }
        public virtual DbSet<Organizations> Organizations { get; set; }
        public virtual DbSet<Pairings> Pairings { get; set; }
        public virtual DbSet<Rounds> Rounds { get; set; }
        public virtual DbSet<Tournaments> Tournaments { get; set; }

        public TournamentMasterDBContext(DbContextOptions<TournamentMasterDBContext> options) : base(options) { }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<AccountOrganization>(entity =>
            {
                entity.HasKey(e => new { e.AccountsId, e.OrganizationsId });

                entity.HasIndex(e => e.OrganizationsId)
                    .HasName("IX_FK_AccountOrganization_Organization");

                entity.Property(e => e.AccountsId).HasColumnName("Accounts_Id");

                entity.Property(e => e.OrganizationsId).HasColumnName("Organizations_Id");

                entity.HasOne(d => d.Accounts)
                    .WithMany(p => p.AccountOrganization)
                    .HasForeignKey(d => d.AccountsId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_AccountOrganization_Account");

                entity.HasOne(d => d.Organizations)
                    .WithMany(p => p.AccountOrganization)
                    .HasForeignKey(d => d.OrganizationsId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_AccountOrganization_Organization");
            });

            modelBuilder.Entity<Competitors>(entity =>
            {
                entity.HasIndex(e => e.OrganizationId)
                    .HasName("IX_FK_OrganizationCompetitor");

                entity.Property(e => e.Email)
                    .IsRequired()
                    .HasMaxLength(50);

                entity.Property(e => e.FirstName)
                    .IsRequired()
                    .HasMaxLength(50);

                entity.Property(e => e.Gender).IsRequired();

                entity.Property(e => e.LastName)
                    .IsRequired()
                    .HasMaxLength(50);

                entity.HasOne(d => d.Organization)
                    .WithMany(p => p.Competitors)
                    .HasForeignKey(d => d.OrganizationId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_OrganizationCompetitor");
            });

            modelBuilder.Entity<CompetitorTournament>(entity =>
            {
                entity.HasKey(e => new { e.CompetitorsId, e.TournamentsId });

                entity.HasIndex(e => e.TournamentsId)
                    .HasName("IX_FK_CompetitorTournament_Tournament");

                entity.Property(e => e.CompetitorsId).HasColumnName("Competitors_Id");

                entity.Property(e => e.TournamentsId).HasColumnName("Tournaments_Id");

                entity.HasOne(d => d.Competitors)
                    .WithMany(p => p.CompetitorTournament)
                    .HasForeignKey(d => d.CompetitorsId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_CompetitorTournament_Competitor");

                entity.HasOne(d => d.Tournaments)
                    .WithMany(p => p.CompetitorTournament)
                    .HasForeignKey(d => d.TournamentsId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_CompetitorTournament_Tournament");
            });

            modelBuilder.Entity<Organizations>(entity =>
            {
                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(50);
            });

            modelBuilder.Entity<Pairings>(entity =>
            {
                entity.HasIndex(e => e.CompetitorId1)
                    .HasName("IX_FK_PairingCompetitor");

                entity.HasIndex(e => e.CompetitorId2)
                    .HasName("IX_FK_PairingCompetitor2");

                entity.HasIndex(e => e.RoundId)
                    .HasName("IX_FK_RoundPairing");

                entity.HasOne(d => d.CompetitorId1Navigation)
                    .WithMany(p => p.PairingsCompetitorId1Navigation)
                    .HasForeignKey(d => d.CompetitorId1)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_PairingCompetitor");

                entity.HasOne(d => d.CompetitorId2Navigation)
                    .WithMany(p => p.PairingsCompetitorId2Navigation)
                    .HasForeignKey(d => d.CompetitorId2)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_PairingCompetitor2");

                entity.HasOne(d => d.Round)
                    .WithMany(p => p.Pairings)
                    .HasForeignKey(d => d.RoundId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_RoundPairing");
            });

            modelBuilder.Entity<Rounds>(entity =>
            {
                entity.HasIndex(e => e.TournamentId)
                    .HasName("IX_FK_TournamentRound");

                entity.HasOne(d => d.Tournament)
                    .WithMany(p => p.Rounds)
                    .HasForeignKey(d => d.TournamentId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_TournamentRound");
            });

            modelBuilder.Entity<Tournaments>(entity =>
            {
                entity.HasIndex(e => e.OrganizationId)
                    .HasName("IX_FK_OrganizationTournament");

                entity.Property(e => e.StartDate).HasColumnType("datetime");

                entity.HasOne(d => d.Organization)
                    .WithMany(p => p.Tournaments)
                    .HasForeignKey(d => d.OrganizationId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_OrganizationTournament");
            });
        }
    }
}
