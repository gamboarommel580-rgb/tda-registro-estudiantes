namespace PedidoApp.Domain;

public class SinDescuento : IPoliticaDescuento
{
    public bool Aplica(Pedido pedido) => true;

    public decimal Aplicar(decimal subtotal) => subtotal;
}
