
using LibroFacil.Domain.Entities;
using Microsoft.EntityFrameworkCore;

namespace LibroFacil.Infrastructure.Persistence
{
    public class LibroFacilDbContext : DbContext
    {
        public LibroFacilDbContext(DbContextOptions<LibroFacilDbContext> options) : base(options)
        {
        }

        public DbSet<Libro> Libros => Set<Libro>();

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Libro>(entity =>
            {
                entity.ToTable("Libros");
                entity.HasKey(l => l.Id);
                
                entity.Property(l => l.Isbn)
                      .IsRequired()
                      .HasMaxLength(20);

                entity.HasIndex(l => l.Isbn)
                      .IsUnique();

                entity.Property(l => l.Titulo)
                      .IsRequired()
                      .HasMaxLength(200);

                entity.Property(l => l.Autor)
                      .IsRequired()
                      .HasMaxLength(150);

                entity.Property(l => l.AnioPublicacion)
                      .IsRequired();

                entity.Property(l => l.Stock)
                      .IsRequired();
            });
        }
    }
}