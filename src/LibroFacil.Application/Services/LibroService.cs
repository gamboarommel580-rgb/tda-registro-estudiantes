using System.Collections.Generic;
using System.Threading.Tasks;
using LibroFacil.Application.Interfaces;
using LibroFacil.Domain.Entities;
using LibroFacil.Domain.Exceptions;

namespace LibroFacil.Application.Services
{
    public class LibroService : ILibroService
    {
        private readonly ILibroRepository _libroRepository;

        public LibroService(ILibroRepository libroRepository)
        {
            _libroRepository = libroRepository;
        }

        public async Task<IEnumerable<Libro>> ObtenerTodosAsync()
        {
            return await _libroRepository.ObtenerTodosAsync();
        }

        public async Task<Libro?> ObtenerPorIdAsync(int id)
        {
            return await _libroRepository.ObtenerPorIdAsync(id);
        }

        public async Task<Libro> CrearAsync(string isbn, string titulo, string autor, int anioPublicacion, int stock)
        {

            var libroExistente = await _libroRepository.ObtenerPorIsbnAsync(isbn);
            if (libroExistente != null)
                throw new DomainException("Ya existe un libro registrado con el mismo ISBN.");

            var nuevoLibro = new Libro(isbn, titulo, autor, anioPublicacion, stock);

            await _libroRepository.AgregarAsync(nuevoLibro);
            return nuevoLibro;
        }

        public async Task ActualizarAsync(int id, string isbn, string titulo, string autor, int anioPublicacion, int stock)
        {
            var libroExistente = await _libroRepository.ObtenerPorIdAsync(id);
            if (libroExistente == null)
                throw new LibroNoEncontradoException(id);

            var libroConMismoIsbn = await _libroRepository.ObtenerPorIsbnAsync(isbn);
            if (libroConMismoIsbn != null && libroConMismoIsbn.Id != id)
                throw new DomainException("El ISBN especificado ya pertenece a otro libro registrado.");

            libroExistente.Actualizar(isbn, titulo, autor, anioPublicacion, stock);
            await _libroRepository.ActualizarAsync(libroExistente);
        }

        public async Task EliminarAsync(int id)
        {
            var libroExistente = await _libroRepository.ObtenerPorIdAsync(id);
            if (libroExistente == null)
                throw new LibroNoEncontradoException(id);

            await _libroRepository.EliminarAsync(libroExistente);
        }
    }
}
