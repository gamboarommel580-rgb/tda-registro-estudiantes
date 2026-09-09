using System.Collections.Generic;
using System.Threading.Tasks;
using LibroFacil.Domain.Entities;

namespace LibroFacil.Application.Interfaces
{
    public interface ILibroService
    {
        Task<IEnumerable<Libro>> ObtenerTodosAsync();
        Task<Libro?> ObtenerPorIdAsync(int id);
        Task<Libro> CrearAsync(string isbn, string titulo, string autor, int anioPublicacion, int stock);
        Task ActualizarAsync(int id, string isbn, string titulo, string autor, int anioPublicacion, int stock);
        Task EliminarAsync(int id);
    }
}
