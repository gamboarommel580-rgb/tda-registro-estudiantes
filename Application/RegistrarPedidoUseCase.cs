using PedidoApp.Domain;

namespace PedidoApp.Application;

// SRP: coordina el registro (no calcula, no guarda, no imprime).
// DIP: depende de IPedidoRepository e INotificadorPedido, no de clases concretas.
public class RegistrarPedidoUseCase : IRegistroPedido
{
    private readonly CalculadoraTotal _calculadora;
    private readonly IPedidoRepository _repository;
    private readonly INotificadorPedido _notificador;

    public RegistrarPedidoUseCase(
        CalculadoraTotal calculadora,
        IPedidoRepository repository,
        INotificadorPedido notificador)
    {
        _calculadora = calculadora;
        _repository = repository;
        _notificador = notificador;
    }

    public decimal Ejecutar(Pedido pedido)
    {
        pedido.Confirmar();                              // el dominio protege sus reglas
        decimal total = _calculadora.Calcular(pedido);   // el calculo esta delegado
        _repository.Guardar(pedido, total);              // a traves del puerto
        _notificador.NotificarRegistro(pedido, total);   // a traves del puerto
        return total;
    }
}
