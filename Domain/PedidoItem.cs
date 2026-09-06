namespace PedidoApp.Domain;

// DDD: el item protege sus propias reglas. Sin setters publicos.
public class PedidoItem
{
    public string Producto { get; private set; }
    public decimal Precio { get; private set; }
    public int Cantidad { get; private set; }

    public PedidoItem(string producto, decimal precio, int cantidad)
    {
        if (string.IsNullOrWhiteSpace(producto))
            throw new Exception("Producto obligatorio");

        if (precio <= 0)
            throw new Exception("Precio incorrecto");

        if (cantidad <= 0)
            throw new Exception("Cantidad incorrecta");

        Producto = producto;
        Precio = precio;
        Cantidad = cantidad;
    }

    // Metodo con significado de negocio.
    public decimal CalcularSubtotal()
    {
        return Precio * Cantidad;
    }
}
