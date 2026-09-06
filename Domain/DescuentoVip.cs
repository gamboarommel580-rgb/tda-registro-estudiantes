namespace PedidoApp.Domain;

public class DescuentoVip : IPoliticaDescuento
{
    private const decimal FactorVip = 0.90m;

    public bool Aplica(Pedido pedido) => pedido.TipoCliente == TipoCliente.Vip;

    public decimal Aplicar(decimal subtotal) => subtotal * FactorVip;
}
