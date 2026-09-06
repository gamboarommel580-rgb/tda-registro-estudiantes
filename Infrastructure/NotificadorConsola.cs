using PedidoApp.Application;
using PedidoApp.Domain;

namespace PedidoApp.Infrastructure;

// ISP: implementa SOLO INotificadorPedido. Sin NotSupportedException.
public class NotificadorConsola : INotificadorPedido
{
    public void NotificarRegistro(Pedido pedido, decimal total)
    {
        Console.WriteLine($"Pedido registrado: {pedido.Cliente}");
        Console.WriteLine($"Total: {total}");
    }
}
