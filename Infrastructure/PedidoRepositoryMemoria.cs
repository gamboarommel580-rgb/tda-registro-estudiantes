using PedidoApp.Application;
using PedidoApp.Domain;

namespace PedidoApp.Infrastructure;

// Adaptador de salida: guarda el pedido en una lista.
public class PedidoRepositoryMemoria : IPedidoRepository
{
    private readonly List<string> _pedidos = new();

    public void Guardar(Pedido pedido, decimal total)
    {
        _pedidos.Add($"{pedido.Cliente} - {total}");
    }

    public IReadOnlyCollection<string> ObtenerTodos() => _pedidos.AsReadOnly();
}
