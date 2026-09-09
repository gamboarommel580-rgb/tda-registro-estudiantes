namespace LibroFacil.Api.DTOs
{
    public class ActualizarLibroDto
    {
        public string Isbn { get; set; } = string.Empty;
        public string Titulo { get; set; } = string.Empty;
        public string Autor { get; set; } = string.Empty;
        public int AnioPublicacion { get; set; }
        public int Stock { get; set; }
    }
}