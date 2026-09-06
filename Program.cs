using PedidoApp.Application;
using PedidoApp.Domain;
using PedidoApp.Infrastructure;

namespace PedidoApp;

// Adaptador de entrada + Composition Root:
// unico lugar donde se hace "new" de implementaciones concretas.
public class Program
{
    public static void Main()
    {
        List<IPoliticaDescuento> politicas = new List<IPoliticaDescuento>
        {
            new DescuentoVip(),
            new SinDescuento()
        };

        CalculadoraTotal calculadora = new CalculadoraTotal(politicas);
        IPedidoRepository repository = new PedidoRepositoryMemoria();
        INotificadorPedido notificador = new NotificadorConsola();

        IRegistroPedido registrarPedido =
            new RegistrarPedidoUseCase(calculadora, repository, notificador);

        // ---------- CASO 1: Pedido NORMAL valido ----------
        Console.WriteLine("=== CASO 1: Pedido NORMAL valido ===");
        try
        {
            Pedido pedido = new Pedido("Ana Perez", TipoCliente.Normal);
            pedido.AgregarItem("Mouse", 20m, 2);
            pedido.AgregarItem("Teclado", 30m, 1);

            registrarPedido.Ejecutar(pedido);
            Console.WriteLine($"Subtotal: {pedido.CalcularSubtotal()} | Estado: {pedido.Estado}");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Pedido rechazado: {ex.Message}");
        }

        // ---------- CASO 2: Pedido VIP valido ----------
        Console.WriteLine();
        Console.WriteLine("=== CASO 2: Pedido VIP valido ===");
        try
        {
            Pedido pedido = new Pedido("Luis Torres", TipoCliente.Vip);
            pedido.AgregarItem("Mouse", 20m, 2);
            pedido.AgregarItem("Teclado", 30m, 1);

            registrarPedido.Ejecutar(pedido);
            Console.WriteLine($"Subtotal: {pedido.CalcularSubtotal()} | Descuento VIP aplicado (-10%)");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Pedido rechazado: {ex.Message}");
        }

        // ---------- CASO 3: Pedido con cantidad 0 ----------
        Console.WriteLine();
        Console.WriteLine("=== CASO 3: Pedido con cantidad 0 ===");
        try
        {
            Pedido pedido = new Pedido("Maria Lopez", TipoCliente.Normal);
            pedido.AgregarItem("Monitor", 150m, 0);

            registrarPedido.Ejecutar(pedido);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Pedido rechazado por el dominio: {ex.Message}");
        }

        // ---------- CASO 4: RetiroLocal (LSP) ----------
        Console.WriteLine();
        Console.WriteLine("=== CASO 4: RetiroLocal ===");
        IMetodoEntrega entrega = new RetiroLocal();
        Console.WriteLine($"Costo de entrega: {entrega.CalcularCosto()}");
        Console.WriteLine("RetiroLocal no expone ProgramarDireccion: no lanza NotSupportedException.");

        // ---------- Evidencia del repositorio ----------
        Console.WriteLine();
        Console.WriteLine("=== Pedidos guardados en PedidoRepositoryMemoria ===");
        foreach (string registro in repository.ObtenerTodos())
            Console.WriteLine(registro);
    }
}
