using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using LibroFacil.Api.DTOs;
using LibroFacil.Application.Interfaces;
using LibroFacil.Domain.Entities;
using LibroFacil.Domain.Exceptions;
using Microsoft.AspNetCore.Mvc;

namespace LibroFacil.Api.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class LibrosController : ControllerBase
    {
        // Depende de la abstracción ILibroService, no de la clase concreta.
        private readonly ILibroService _libroService;

        public LibrosController(ILibroService libroService)
        {
            _libroService = libroService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<LibroRespuestaDto>>> ObtenerTodos()
        {
            var libros = await _libroService.ObtenerTodosAsync();
            return Ok(libros.Select(MapearADto));
        }

        [HttpGet("{id:int}")]
        public async Task<ActionResult<LibroRespuestaDto>> ObtenerPorId(int id)
        {
            var libro = await _libroService.ObtenerPorIdAsync(id);
            if (libro == null)
                return NotFound(new { error = $"No se encontró un libro con Id {id}." });

            return Ok(MapearADto(libro));
        }

        [HttpPost]
        public async Task<ActionResult<LibroRespuestaDto>> Crear([FromBody] CrearLibroDto dto)
        {
            try
            {
                var nuevoLibro = await _libroService.CrearAsync(
                    dto.Isbn, dto.Titulo, dto.Autor, dto.AnioPublicacion, dto.Stock);

                return CreatedAtAction(
                    nameof(ObtenerPorId),
                    new { id = nuevoLibro.Id },
                    MapearADto(nuevoLibro));
            }
            catch (DomainException ex)
            {
                return BadRequest(new { error = ex.Message });
            }
        }

        [HttpPut("{id:int}")]
        public async Task<IActionResult> Actualizar(int id, [FromBody] ActualizarLibroDto dto)
        {
            try
            {
                await _libroService.ActualizarAsync(
                    id, dto.Isbn, dto.Titulo, dto.Autor, dto.AnioPublicacion, dto.Stock);

                return NoContent();
            }
            catch (LibroNoEncontradoException ex)
            {
                return NotFound(new { error = ex.Message });
            }
            catch (DomainException ex)
            {
                return BadRequest(new { error = ex.Message });
            }
        }

        [HttpDelete("{id:int}")]
        public async Task<IActionResult> Eliminar(int id)
        {
            try
            {
                await _libroService.EliminarAsync(id);
                return NoContent();
            }
            catch (LibroNoEncontradoException ex)
            {
                return NotFound(new { error = ex.Message });
            }
        }

        private static LibroRespuestaDto MapearADto(Libro libro) => new LibroRespuestaDto
        {
            Id = libro.Id,
            Isbn = libro.Isbn,
            Titulo = libro.Titulo,
            Autor = libro.Autor,
            AnioPublicacion = libro.AnioPublicacion,
            Stock = libro.Stock
        };
    }
}