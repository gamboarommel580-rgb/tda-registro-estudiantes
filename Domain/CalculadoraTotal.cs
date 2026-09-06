namespace PedidoApp.Domain;

// SRP: su unica responsabilidad es calcular el total.
// OCP: no contiene ningun if de tipo de cliente.
public class CalculadoraTotal
{
    private readonly IEnumerable<IPoliticaDescuento> _politicas;

    public CalculadoraTotal(IEnumerable<IPoliticaDescuento> politicas)
    {
        _politicas = politicas;
    }

    public decimal Calcular(Pedido pedido)
    {
        decimal subtotal = pedido.CalcularSubtotal();

        IPoliticaDescuento politica = _politicas.FirstOrDefault(p => p.Aplica(pedido));

        return politica == null ? subtotal : politica.Aplicar(subtotal);
    }
}
