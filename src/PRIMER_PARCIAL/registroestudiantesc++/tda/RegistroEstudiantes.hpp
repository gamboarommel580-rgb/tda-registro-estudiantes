#ifndef REGISTRO_ESTUDIANTES_HPP
#define REGISTRO_ESTUDIANTES_HPP

#include "../model/Estudiante.hpp"

class RegistroEstudiantes {
private:
    static const int CAPACIDAD = 30;
    Estudiante* estudiantes[CAPACIDAD];
    int cantidad;

    int buscarIndicePorId(long long id) const;

public:
    RegistroEstudiantes();
    ~RegistroEstudiantes();

    bool estaVacio() const { return cantidad == 0; }
    bool estaLleno() const { return cantidad >= CAPACIDAD; }
    int getCantidad() const { return cantidad; }
    int getCapacidad() const { return CAPACIDAD; }

    Estudiante* buscarPorId(long long id) const;
    bool registrar(Estudiante* estudiante);
    void listarTodos() const;
    Estudiante** obtenerTodos() const;
    bool modificar(long long id, const std::string& nuevoNombre, int nuevaEdad, double nuevoPromedio);
    bool eliminar(long long id);
    bool verificarIntegridad() const;
};

#endif