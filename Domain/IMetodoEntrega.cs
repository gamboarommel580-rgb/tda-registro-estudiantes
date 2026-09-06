namespace PedidoApp.Domain;

// LSP: capacidad realmente comun a todo metodo de entrega.
public interface IMetodoEntrega
{
    decimal CalcularCosto();
}
