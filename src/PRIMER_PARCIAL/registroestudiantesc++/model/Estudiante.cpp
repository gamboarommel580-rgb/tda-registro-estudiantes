#include "Estudiante.hpp"
#include <iomanip>
#include <stdexcept>

Estudiante::Estudiante(long long id, const std::string& nombre, int edad, double promedio) {
    validarId(id);
    validarNombre(nombre);
    validarEdad(edad);
    validarPromedio(promedio);
    this->id = id;
    this->nombre = nombre;
    this->edad = edad;
    this->promedio = promedio;
}

void Estudiante::setNombre(const std::string& nombre) {
    validarNombre(nombre);
    this->nombre = nombre;
}

void Estudiante::setEdad(int edad) {
    validarEdad(edad);
    this->edad = edad;
}

void Estudiante::setPromedio(double promedio) {
    validarPromedio(promedio);
    this->promedio = promedio;
}

void Estudiante::actualizarDatos(const std::string& nombre, int edad, double promedio) {
    validarNombre(nombre);
    validarEdad(edad);
    validarPromedio(promedio);
    this->nombre = nombre;
    this->edad = edad;
    this->promedio = promedio;
}

void Estudiante::validarId(long long id) {
    if (id < 10000000LL || id > 9999999999LL) {
        throw std::invalid_argument("El ID debe tener entre 8 y 10 digitos.");
    }
}

void Estudiante::validarNombre(const std::string& nombre) {
    if (nombre.empty()) {
        throw std::invalid_argument("El nombre no puede estar vacio.");
    }
    if (nombre.length() < 2) {
        throw std::invalid_argument("El nombre debe tener al menos 2 caracteres.");
    }
}

void Estudiante::validarEdad(int edad) {
    if (edad < 15 || edad > 100) {
        throw std::invalid_argument("La edad debe estar entre 15 y 100 años.");
    }
}

void Estudiante::validarPromedio(double promedio) {
    if (promedio < 0.0 || promedio > 10.0) {
        throw std::invalid_argument("El promedio debe estar entre 0.0 y 10.0.");
    }
}

std::ostream& operator<<(std::ostream& os, const Estudiante& est) {
    os << "ID: " << std::setw(10) << std::left << est.id
       << " | Nombre: " << std::setw(25) << std::left << est.nombre
       << " | Edad: " << std::setw(3) << std::right << est.edad
       << " | Promedio: " << std::fixed << std::setprecision(2) << est.promedio;
    return os;
}