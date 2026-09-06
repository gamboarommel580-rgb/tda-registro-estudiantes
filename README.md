# Tarea — SOLID + DDD + Clean Architecture + Arquitectura Hexagonal — C#

**Nombre completo:** _________________________________

| Dato | Descripción |
|---|---|
| Modalidad | Individual |
| Tecnología | C# / .NET — aplicación de consola |
| Entrega | Un solo PDF con capturas y fragmentos de código |

---

## 1. Objetivo

Refactorizar el código inicial aplicando SRP, OCP, LSP, ISP y DIP; enriquecer el dominio mediante una refactorización DDD básica de un modelo anémico a un modelo que expresa el negocio; luego organizar la solución con una separación básica inspirada en Clean Architecture e identificar los elementos principales de Arquitectura Hexagonal.

---

## 2. Código inicial

Ejecución del código inicial antes de refactorizar:

```
cd PedidoApp.Inicial
dotnet run
```

![Captura del código inicial ejecutándose](capturas/01_codigo_inicial.png)

El programa funciona: registra el pedido NORMAL (total 70) y el VIP (total 63). Sin embargo, `RetiroLocal.ProgramarDireccion()` lanza `NotSupportedException` y `NotificadorConsola` implementa métodos que no usa.

---

## 3. Actividad

### 3.1 Identificar problemas

| # | Problema | Consecuencia | Principio |
|---|---|---|---|
| 1 | `PedidoManager.Registrar()` valida, calcula, aplica el descuento, guarda en `_pedidos` y escribe en consola, todo en un solo método. | La clase tiene varias razones para cambiar; tocar el formato de salida obliga a tocar el cálculo. | SRP |
| 2 | El descuento se resuelve con `if (tipoCliente == "VIP") total *= 0.90m;` dentro del cálculo. | Cada tipo de cliente nuevo obliga a modificar código ya probado. | OCP |
| 3 | `RetiroLocal` hereda `ProgramarDireccion()` y solo puede lanzar `NotSupportedException`. | Un cliente que recibe `MetodoEntrega` falla según el subtipo que le entreguen. | LSP |
| 4 | `IServicioPedido` obliga a implementar `Registrar()`, `Notificar()` y `GenerarPdf()`. | `NotificadorConsola` implementa métodos artificiales que no necesita. | ISP |
| 5 | `PedidoManager` administra su propia lista `_pedidos` y depende directamente de `Console`. | El negocio conoce los detalles concretos; no se puede sustituir la persistencia por un fake. | DIP |

---

### 3.2 SRP

`PedidoManager` se separó en cuatro componentes con una responsabilidad cada uno.

| Componente | Responsabilidad |
|---|---|
| `Pedido` / `PedidoItem` | Datos y validaciones básicas. |
| `CalculadoraTotal` | Calcula el total. |
| `RegistrarPedidoUseCase` | Coordina el registro. |
| `PedidoRepositoryMemoria` | Guarda el pedido en una lista. |

**ANTES**

```csharp
public decimal Registrar(string cliente, string tipoCliente, List<PedidoItem> items)
{
    if (string.IsNullOrWhiteSpace(cliente)) throw new Exception("Cliente obligatorio");
    if (items == null || items.Count == 0) throw new Exception("Pedido ");

    decimal total = 0m;
    foreach (PedidoItem item in items)
    {
        if (item.Cantidad <= 0) throw new Exception("Cantidad incorrecta");
        total += item.Precio * item.Cantidad;          // calcula
    }

    if (tipoCliente == "VIP") total *= 0.90m;          // descuenta

    _pedidos.Add($"{cliente} - {total}");              // guarda
    Console.WriteLine($"Pedido registrado: {cliente}"); // notifica
    return total;
}
```

**DESPUÉS**

```csharp
// Domain/CalculadoraTotal.cs -> solo calcula
public decimal Calcular(Pedido pedido)
{
    decimal subtotal = pedido.CalcularSubtotal();
    IPoliticaDescuento politica = _politicas.FirstOrDefault(p => p.Aplica(pedido));
    return politica == null ? subtotal : politica.Aplicar(subtotal);
}

// Application/RegistrarPedidoUseCase.cs -> solo coordina
public decimal Ejecutar(Pedido pedido)
{
    pedido.Confirmar();
    decimal total = _calculadora.Calcular(pedido);
    _repository.Guardar(pedido, total);
    _notificador.NotificarRegistro(pedido, total);
    return total;
}

// Infrastructure/PedidoRepositoryMemoria.cs -> solo guarda
public void Guardar(Pedido pedido, decimal total)
{
    _pedidos.Add($"{pedido.Cliente} - {total}");
}
```

---

### 3.3 OCP

**ANTES**

```csharp
if (tipoCliente == "VIP")
    total *= 0.90m;
```

**DESPUÉS**

```csharp
public interface IPoliticaDescuento
{
    bool Aplica(Pedido pedido);
    decimal Aplicar(decimal subtotal);
}

public class DescuentoVip : IPoliticaDescuento
{
    private const decimal FactorVip = 0.90m;
    public bool Aplica(Pedido pedido) => pedido.TipoCliente == TipoCliente.Vip;
    public decimal Aplicar(decimal subtotal) => subtotal * FactorVip;
}

public class SinDescuento : IPoliticaDescuento
{
    public bool Aplica(Pedido pedido) => true;
    public decimal Aplicar(decimal subtotal) => subtotal;
}
```

**Cómo agregar `DescuentoMayorista` sin modificar la calculadora (2 líneas):**

> Bastaría con crear la clase `DescuentoMayorista : IPoliticaDescuento` con su propio `Aplica()` y `Aplicar()`, sin abrir `CalculadoraTotal`.
> Solo se registra esa política en la lista de `Program.cs`: la calculadora queda cerrada a modificación y abierta a extensión.

---

### 3.4 LSP

**ANTES**

```csharp
public abstract class MetodoEntrega
{
    public abstract decimal CalcularCosto();
    public abstract void ProgramarDireccion(string direccion);
}

public class RetiroLocal : MetodoEntrega
{
    public override decimal CalcularCosto() => 0m;

    public override void ProgramarDireccion(string direccion)
    {
        throw new NotSupportedException();
    }
}
```

**DESPUÉS**

```csharp
// Capacidad realmente común
public interface IMetodoEntrega
{
    decimal CalcularCosto();
}

// Capacidad específica, solo para entregas con dirección
public interface IEntregaConDireccion
{
    void ProgramarDireccion(string direccion);
}

public class RetiroLocal : IMetodoEntrega
{
    public decimal CalcularCosto() => 0m;
}
```

`RetiroLocal` ya no hereda un método que no puede cumplir, por lo que desaparece el `NotSupportedException`.

---

### 3.5 ISP

**ANTES**

```csharp
public interface IServicioPedido
{
    void Registrar();
    void Notificar();
    void GenerarPdf();
}

public class NotificadorConsola : IServicioPedido
{
    public void Notificar() => Console.WriteLine("Pedido notificado");
    public void Registrar() => throw new NotSupportedException();
    public void GenerarPdf() => throw new NotSupportedException();
}
```

**DESPUÉS**

```csharp
public interface INotificadorPedido
{
    void NotificarRegistro(Pedido pedido, decimal total);
}

public interface IRegistroPedido
{
    decimal Ejecutar(Pedido pedido);
}

public class NotificadorConsola : INotificadorPedido
{
    public void NotificarRegistro(Pedido pedido, decimal total)
    {
        Console.WriteLine($"Pedido registrado: {pedido.Cliente}");
        Console.WriteLine($"Total: {total}");
    }
}
```

`NotificadorConsola` implementa solamente lo que necesita: no queda ni un `NotSupportedException`.

---

### 3.6 DIP + inyección de dependencias

**ANTES**

```csharp
private readonly List<string> _pedidos = new();   // el caso de uso ES la persistencia
_pedidos.Add($"{cliente} - {total}");
Console.WriteLine($"Pedido registrado: {cliente}");
```

**DESPUÉS — inyección por constructor**

```csharp
// Application/IPedidoRepository.cs
public interface IPedidoRepository
{
    void Guardar(Pedido pedido, decimal total);
    IReadOnlyCollection<string> ObtenerTodos();
}

// Application/RegistrarPedidoUseCase.cs
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
}

// Program.cs -> crea la implementación y la entrega al caso de uso
IPedidoRepository repository = new PedidoRepositoryMemoria();
INotificadorPedido notificador = new NotificadorConsola();
IRegistroPedido registrarPedido = new RegistrarPedidoUseCase(calculadora, repository, notificador);
```

El caso de uso nunca ejecuta `new PedidoRepositoryMemoria()`. Para pruebas se le puede entregar un fake sin modificarlo.

---

## 4. DDD — De un modelo anémico a un modelo que expresa el negocio

### 4.1 Modelo anémico (ANTES)

```csharp
public class PedidoItem
{
    public string Producto { get; set; } = "";
    public decimal Precio { get; set; }
    public int Cantidad { get; set; }
}
```

Almacena información, pero no protege por sí mismo la cantidad válida, el precio permitido ni los datos obligatorios.

### 4.2 Refactorización del dominio (DESPUÉS)

```csharp
public class PedidoItem
{
    public string Producto { get; private set; }
    public decimal Precio { get; private set; }
    public int Cantidad { get; private set; }

    public PedidoItem(string producto, decimal precio, int cantidad)
    {
        if (string.IsNullOrWhiteSpace(producto)) throw new Exception("Producto obligatorio");
        if (precio <= 0) throw new Exception("Precio incorrecto");
        if (cantidad <= 0) throw new Exception("Cantidad incorrecta");

        Producto = producto;
        Precio = precio;
        Cantidad = cantidad;
    }

    public decimal CalcularSubtotal()
    {
        return Precio * Cantidad;
    }
}
```

```csharp
public class Pedido
{
    private readonly List<PedidoItem> _items = new();

    public string Cliente { get; private set; }
    public IReadOnlyCollection<PedidoItem> Items => _items.AsReadOnly();

    public void AgregarItem(string producto, decimal precio, int cantidad)
    {
        _items.Add(new PedidoItem(producto, precio, cantidad));
    }

    public void Confirmar()
    {
        if (_items.Count == 0) throw new Exception("Pedido sin items");
        Estado = EstadoPedido.Confirmado;
    }
}
```

| Regla movida al dominio | Dónde vive ahora |
|---|---|
| Producto obligatorio | `PedidoItem` (constructor) |
| Precio válido | `PedidoItem` (constructor) |
| Cantidad mayor que cero | `PedidoItem` (constructor) |
| Cliente obligatorio | `Pedido` (constructor) |
| No confirmar un pedido vacío | `Pedido.Confirmar()` |

**Explicación (máximo 3 líneas):**

> Al dominio pasaron las reglas de `PedidoItem` (producto obligatorio, precio válido y cantidad mayor que cero) y las de `Pedido` (cliente obligatorio y no confirmar un pedido vacío).
> El estado dejó de ser público: los setters son privados y la lista de ítems se expone como `IReadOnlyCollection`, por lo que solo cambia mediante `AgregarItem()`.
> El modelo expresa mejor el negocio porque cada regla vive junto al dato que protege y aparecen métodos con significado propio como `AgregarItem()`, `Confirmar()` y `CalcularSubtotal()`.

---

## 5. Clean Architecture

![Captura de la estructura final de carpetas](capturas/02_estructura.png)

```
PedidoApp/
│
├── Domain/
│   ├── Pedido.cs
│   ├── PedidoItem.cs
│   ├── CalculadoraTotal.cs
│   ├── IPoliticaDescuento.cs
│   ├── DescuentoVip.cs
│   ├── SinDescuento.cs
│   ├── IMetodoEntrega.cs
│   ├── IEntregaConDireccion.cs
│   └── RetiroLocal.cs
│
├── Application/
│   ├── RegistrarPedidoUseCase.cs
│   ├── IRegistroPedido.cs
│   ├── IPedidoRepository.cs
│   └── INotificadorPedido.cs
│
├── Infrastructure/
│   ├── PedidoRepositoryMemoria.cs
│   └── NotificadorConsola.cs
│
└── Program.cs
```

| Carpeta | Responsabilidad |
|---|---|
| Domain | Entidades y reglas del negocio del pedido. |
| Application | Coordinar el registro y declarar `IPedidoRepository`. |
| Infrastructure | Implementar `PedidoRepositoryMemoria`. |
| Program.cs | Crear objetos y ejecutar el ejemplo. |

**Explicación (máximo 4 líneas):**

> `Domain` contiene `Pedido`, `PedidoItem` y las reglas del negocio, y no declara ningún `using` hacia `Application` ni `Infrastructure`.
> `Application` coordina el registro y declara `IPedidoRepository`, es decir, expresa lo que necesita sin saber cómo se implementa.
> `Infrastructure` implementa `PedidoRepositoryMemoria`, por lo que el detalle técnico depende de la abstracción y no al revés.
> `Program.cs` es el único que conoce las clases concretas, así que todas las dependencias apuntan hacia el núcleo.

---

## 6. Arquitectura Hexagonal

| Elemento hexagonal | En este taller |
|---|---|
| Adaptador de entrada | `Program.cs` |
| Caso de uso / núcleo | `RegistrarPedidoUseCase` |
| Puerto de salida | `IPedidoRepository` |
| Adaptador de salida | `PedidoRepositoryMemoria` |
| Dominio | `Pedido` y `PedidoItem` |

**Esquema**

```
Program.cs
    ↓
RegistrarPedidoUseCase
    ↓
IPedidoRepository
    ↑
PedidoRepositoryMemoria

Domain: Pedido / PedidoItem
```

Las flechas hacia abajo son invocaciones del núcleo hacia su puerto; la flecha hacia arriba es la implementación. Cambiar `PedidoRepositoryMemoria` por un repositorio SQL no obliga a editar el núcleo.

---

## 7. Funcionamiento

```
cd PedidoApp
dotnet run
```

| Caso | Resultado esperado | Resultado obtenido |
|---|---|---|
| Pedido NORMAL válido | Se registra y muestra el total. | Total 70 ✔ |
| Pedido VIP válido | Se registra y se observa el descuento. | Subtotal 70 → Total 63 ✔ |
| Pedido con cantidad 0 | Se rechaza. | "Cantidad incorrecta" ✔ |
| RetiroLocal | Calcula costo 0 sin `NotSupportedException`. | Costo 0, sin excepción ✔ |

**Caso 1 — Pedido NORMAL válido**

![Caso 1](capturas/03_caso1_normal.png)

**Caso 2 — Pedido VIP válido**

![Caso 2](capturas/04_caso2_vip.png)

**Caso 3 — Pedido con cantidad 0**

![Caso 3](capturas/05_caso3_cantidad_cero.png)

**Caso 4 — RetiroLocal**

![Caso 4](capturas/06_caso4_retirolocal.png)

Salida esperada de referencia:

```
=== CASO 1: Pedido NORMAL valido ===
Pedido registrado: Ana Perez
Total: 70
Subtotal: 70 | Estado: Confirmado

=== CASO 2: Pedido VIP valido ===
Pedido registrado: Luis Torres
Total: 63,0
Subtotal: 70 | Descuento VIP aplicado (-10%)

=== CASO 3: Pedido con cantidad 0 ===
Pedido rechazado por el dominio: Cantidad incorrecta

=== CASO 4: RetiroLocal ===
Costo de entrega: 0
RetiroLocal no expone ProgramarDireccion: no lanza NotSupportedException.

=== Pedidos guardados en PedidoRepositoryMemoria ===
Ana Perez - 70
Luis Torres - 63,0
```

---

## 8. Conclusión personal

> Refactorizar `PedidoManager` me mostró que un código que funciona no siempre es un código mantenible: el total siguió siendo 70 y 63, pero la estructura dejó de depender de una sola clase.
> Con SRP y OCP entendí que separar responsabilidades y reemplazar el `if` del descuento por políticas permite agregar una regla nueva sin volver a probar lo que ya servía.
> LSP e ISP me enseñaron que un `NotSupportedException` casi siempre indica un contrato mal modelado y no un caso excepcional real.
> Con DIP comprobé que, al recibir `IPedidoRepository` por constructor, el caso de uso se puede probar con un repositorio falso sin tocar el negocio.
> Finalmente, DDD y Clean Architecture no son carpetas: son la decisión de que el dominio proteja sus propias reglas y de que los detalles técnicos dependan del núcleo.
