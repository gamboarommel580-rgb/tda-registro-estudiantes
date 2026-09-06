namespace PedidoApp.Domain;

// LSP: capacidad especifica, solo para entregas que si manejan direccion.
public interface IEntregaConDireccion
{
    void ProgramarDireccion(string direccion);
}
