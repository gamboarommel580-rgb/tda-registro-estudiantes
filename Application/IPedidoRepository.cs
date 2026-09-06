using PedidoApp.Domain;

namespace PedidoApp.Application;

// DIP / Puerto de salida: Application dice QUE necesita, no COMO se hace.
public interface IPedidoRepository
{
    void Guardar(Pedido pedido, decimal total);
    IReadOnlyCollection<string> ObtenerTodos();
}
