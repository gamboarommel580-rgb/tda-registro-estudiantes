using System;
using LibroFacil.Domain.Exceptions;

namespace LibroFacil.Domain.Entities
{
    public class Libro
    {
        public int Id { get; private set; }
        public string Isbn { get; private set; } = string.Empty;
        public string Titulo { get; private set; } = string.Empty;
        public string Autor { get; private set; } = string.Empty;
        public int AnioPublicacion { get; private set; }
        public int Stock { get; private set; }

        private Libro() { }

        public Libro(string isbn, string titulo, string autor, int anioPublicacion, int stock)
        {
            AsignarDatos(isbn, titulo, autor, anioPublicacion, stock);
        }

        public void Actualizar(string isbn, string titulo, string autor, int anioPublicacion, int stock)
        {
            AsignarDatos(isbn, titulo, autor, anioPublicacion, stock);
        }

        private void AsignarDatos(string isbn, string titulo, string autor, int anioPublicacion, int stock)
        {

            ValidarIsbn(isbn);
            ValidarTitulo(titulo);
            ValidarAutor(autor);
            ValidarAnioPublicacion(anioPublicacion);
            ValidarStock(stock);

            Isbn = isbn.Trim();
            Titulo = titulo.Trim();
            Autor = autor.Trim();
            AnioPublicacion = anioPublicacion;
            Stock = stock;
        }

        private static void ValidarIsbn(string isbn)
        {
            if (string.IsNullOrWhiteSpace(isbn))
                throw new DomainException("El ISBN es obligatorio.");
        }

        private static void ValidarTitulo(string titulo)
        {
            if (string.IsNullOrWhiteSpace(titulo))
                throw new DomainException("El título es obligatorio.");
        }

        private static void ValidarAutor(string autor)
        {
            if (string.IsNullOrWhiteSpace(autor))
                throw new DomainException("El autor es obligatorio.");
        }

        private static void ValidarAnioPublicacion(int anioPublicacion)
        {
            int anioActual = DateTime.UtcNow.Year;
            if (anioPublicacion <= 0 || anioPublicacion > anioActual)
                throw new DomainException($"El año de publicación debe ser mayor a 0 y no puede ser mayor al año actual ({anioActual}).");
        }

        private static void ValidarStock(int stock)
        {
            if (stock < 0)
                throw new DomainException("El stock no puede ser negativo.");
        }
    }
}
