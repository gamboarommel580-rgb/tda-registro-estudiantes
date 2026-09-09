namespace LibroFacil.Domain.Exceptions
{
    public class LibroNoEncontradoException : DomainException
    {
        public LibroNoEncontradoException(int id)
            : base("No se encontró un libro con Id {id}.")
        {
        }
    }
}
