using LibroFacil.Application.Interfaces;
using LibroFacil.Application.Services;
using LibroFacil.Infrastructure.Persistence;
using LibroFacil.Infrastructure.Repositories;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// 1. Capa de presentación: controladores y Swagger.
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// 2. Infrastructure: EF Core sobre SQL Server.
builder.Services.AddDbContext<LibroFacilDbContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("LibroFacilDb")));

// 3. Inyección de dependencias (DIP):
//    las abstracciones se declaran en Application y se resuelven aquí,
//    en el único punto de la solución que conoce a Infrastructure.
builder.Services.AddScoped<ILibroRepository, LibroRepositoryEf>();
builder.Services.AddScoped<ILibroService, LibroService>();

var app = builder.Build();

app.UseSwagger();
app.UseSwaggerUI(c =>
{
    c.SwaggerEndpoint("/swagger/v1/swagger.json", "LibroFacil API v1");
    c.RoutePrefix = "swagger";
});

app.MapControllers();

app.Run();
