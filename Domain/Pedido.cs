namespace PedidoApp.Domain;

public enum TipoCliente { Normal, Vip }

public enum EstadoPedido { Borrador, Confirmado }

// DDD: el pedido controla su coleccion y sus reglas.
public class Pedido
{
    private readonly List<PedidoItem> _items = new();

    public string Cliente { get; private set; }
    public TipoCliente TipoCliente { get; private set; }
    public EstadoPedido Estado { get; private set; }

    // Vista de solo lectura: nadie puede hacer Items.Add(...) desde afuera.
    public IReadOnlyCollection<PedidoItem> Items => _items.AsReadOnly();

    public Pedido(string cliente, TipoCliente tipoCliente)
    {
        if (string.IsNullOrWhiteSpace(cliente))
            throw new Exception("Cliente obligatorio");

        Cliente = cliente;
        TipoCliente = tipoCliente;
        Estado = EstadoPedido.Borrador;
    }

    // Metodo con significado de negocio.
    public void AgregarItem(string producto, decimal precio, int cantidad)
    {
        _items.Add(new PedidoItem(producto, precio, cantidad));
    }

    public decimal CalcularSubtotal()
    {
        decimal subtotal = 0m;

        foreach (PedidoItem item in _items)
            subtotal += item.CalcularSubtotal();

        return subtotal;
    }

    // Metodo con significado de negocio: protege la regla del pedido vacio.
    public void Confirmar()
    {
        if (_items.Count == 0)
            throw new Exception("Pedido sin items");

        Estado = EstadoPedido.Confirmado;
    }
}
