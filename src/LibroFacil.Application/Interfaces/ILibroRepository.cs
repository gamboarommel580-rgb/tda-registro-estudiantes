using System.Collections.Generic;
using System.Threading.Tasks;
using LibroFacil.Domain.Entities;

namespace LibroFacil.Application.Interfaces
{
    public interface ILibroRepository
    {
        Task<IEnumerable<Libro>> ObtenerTodosAsync();
        Task<Libro?> ObtenerPorIdAsync(int id);
        Task<Libro?> ObtenerPorIsbnAsync(string isbn);
        Task AgregarAsync(Libro libro);
        Task ActualizarAsync(Libro libro);
        Task EliminarAsync(Libro libro);
    }
}