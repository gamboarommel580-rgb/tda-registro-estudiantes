using PedidoApp.Domain;

namespace PedidoApp.Application;

// ISP: contrato pequeno con una sola capacidad.
public interface INotificadorPedido
{
    void NotificarRegistro(Pedido pedido, decimal total);
}
