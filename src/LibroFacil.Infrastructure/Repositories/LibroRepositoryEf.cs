using System.Collections.Generic;
using System.Threading.Tasks;
using LibroFacil.Application.Interfaces;
using LibroFacil.Domain.Entities;
using LibroFacil.Infrastructure.Persistence;
using Microsoft.EntityFrameworkCore;

namespace LibroFacil.Infrastructure.Repositories
{
    public class LibroRepositoryEf : ILibroRepository
    {
        private readonly LibroFacilDbContext _context;

        public LibroRepositoryEf(LibroFacilDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Libro>> ObtenerTodosAsync()
        {
            return await _context.Libros.AsNoTracking().ToListAsync();
        }

        public async Task<Libro?> ObtenerPorIdAsync(int id)
        {
            return await _context.Libros.FindAsync(id);
        }

        public async Task<Libro?> ObtenerPorIsbnAsync(string isbn)
        {
            return await _context.Libros.FirstOrDefaultAsync(l => l.Isbn == isbn);
        }

        public async Task AgregarAsync(Libro libro)
        {
            await _context.Libros.AddAsync(libro);
            await _context.SaveChangesAsync();
        }

        public async Task ActualizarAsync(Libro libro)
        {
            _context.Libros.Update(libro);
            await _context.SaveChangesAsync();
        }

        public async Task EliminarAsync(Libro libro)
        {
            _context.Libros.Remove(libro);
            await _context.SaveChangesAsync();
        }
    }
}