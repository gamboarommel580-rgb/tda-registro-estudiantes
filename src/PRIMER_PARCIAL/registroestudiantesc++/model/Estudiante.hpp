#ifndef ESTUDIANTE_HPP
#define ESTUDIANTE_HPP

#include <string>
#include <iostream>

class Estudiante {
private:
    long long id;
    std::string nombre;
    int edad;
    double promedio;

    static void validarId(long long id);
    static void validarNombre(const std::string& nombre);
    static void validarEdad(int edad);
    static void validarPromedio(double promedio);

public:
    Estudiante(long long id, const std::string& nombre, int edad, double promedio);

    long long getId() const { return id; }
    std::string getNombre() const { return nombre; }
    int getEdad() const { return edad; }
    double getPromedio() const { return promedio; }

    void setNombre(const std::string& nombre);
    void setEdad(int edad);
    void setPromedio(double promedio);
    void actualizarDatos(const std::string& nombre, int edad, double promedio);

    friend std::ostream& operator<<(std::ostream& os, const Estudiante& est);
};

#endif