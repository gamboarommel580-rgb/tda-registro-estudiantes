using PedidoApp.Domain;

namespace PedidoApp.Application;

// ISP: interfaz relacionada con registro, separada de la notificacion.
public interface IRegistroPedido
{
    decimal Ejecutar(Pedido pedido);
}
