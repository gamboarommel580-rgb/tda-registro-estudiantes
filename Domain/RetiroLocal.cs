namespace PedidoApp.Domain;

// LSP: declara solo la capacidad que soporta.
// Ya no hereda ProgramarDireccion, por lo tanto no lanza NotSupportedException.
public class RetiroLocal : IMetodoEntrega
{
    public decimal CalcularCosto() => 0m;
}
