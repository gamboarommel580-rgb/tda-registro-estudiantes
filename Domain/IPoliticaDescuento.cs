namespace PedidoApp.Domain;

// OCP: contrato comun de descuento.
public interface IPoliticaDescuento
{
    bool Aplica(Pedido pedido);
    decimal Aplicar(decimal subtotal);
}
