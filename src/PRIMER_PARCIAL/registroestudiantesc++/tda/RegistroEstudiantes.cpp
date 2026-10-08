#include "RegistroEstudiantes.hpp"
#include <iostream>
#include <iomanip>

RegistroEstudiantes::RegistroEstudiantes() : cantidad(0) {
    for (int i = 0; i < CAPACIDAD; i++) {
        estudiantes[i] = nullptr;
    }
}

RegistroEstudiantes::~RegistroEstudiantes() {
    for (int i = 0; i < cantidad; i++) {
        delete estudiantes[i];
    }
}

int RegistroEstudiantes::buscarIndicePorId(long long id) const {
    for (int i = 0; i < cantidad; i++) {
        if (estudiantes[i]->getId() == id) {
            return i;
        }
    }
    return -1;
}

Estudiante* RegistroEstudiantes::buscarPorId(long long id) const {
    int indice = buscarIndicePorId(id);
    return (indice != -1) ? estudiantes[indice] : nullptr;
}

bool RegistroEstudiantes::registrar(Estudiante* estudiante) {
    if (estudiante == nullptr) {
        throw std::invalid_argument("El estudiante no puede ser null.");
    }
    if (estaLleno()) {
        std::cout << "Error: Registro lleno. Capacidad: " << CAPACIDAD << std::endl;
        return false;
    }
    if (buscarPorId(estudiante->getId()) != nullptr) {
        std::cout << "Error: ID " << estudiante->getId() << " ya registrado." << std::endl;
        return false;
    }
    estudiantes[cantidad] = estudiante;
    cantidad++;
    std::cout << "Estudiante registrado. Total: " << cantidad << "/" << CAPACIDAD << std::endl;
    return true;
}

void RegistroEstudiantes::listarTodos() const {
    if (estaVacio()) {
        std::cout << "El registro esta vacio." << std::endl;
        return;
    }
    std::cout << "\n" << std::string(80, '=') << std::endl;
    std::cout << "LISTADO DE ESTUDIANTES (" << cantidad << "/" << CAPACIDAD << ")" << std::endl;
    std::cout << std::string(80, '=') << std::endl;
    std::cout << "#   ID           Nombre                    Edad   Promedio" << std::endl;
    std::cout << std::string(80, '-') << std::endl;
    for (int i = 0; i < cantidad; i++) {
        std::cout << std::setw(2) << std::left << (i + 1) << ". ";
        std::cout << *estudiantes[i] << std::endl;
    }
    std::cout << std::string(80, '=') << std::endl;
}

Estudiante** RegistroEstudiantes::obtenerTodos() const {
    Estudiante** copia = new Estudiante*[cantidad];
    for (int i = 0; i < cantidad; i++) {
        copia[i] = estudiantes[i];
    }
    return copia;
}

bool RegistroEstudiantes::modificar(long long id, const std::string& nuevoNombre, int nuevaEdad, double nuevoPromedio) {
    int indice = buscarIndicePorId(id);
    if (indice == -1) {
        std::cout << "Error: No existe estudiante con ID " << id << std::endl;
        return false;
    }
    try {
        estudiantes[indice]->actualizarDatos(nuevoNombre, nuevaEdad, nuevoPromedio);
        std::cout << "Estudiante modificado con exito." << std::endl;
        return true;
    } catch (const std::invalid_argument& e) {
        std::cout << "Error: " << e.what() << std::endl;
        return false;
    }
}

bool RegistroEstudiantes::eliminar(long long id) {
    if (estaVacio()) {
        std::cout << "Error: El registro esta vacio." << std::endl;
        return false;
    }
    int indice = buscarIndicePorId(id);
    if (indice == -1) {
        std::cout << "Error: No existe estudiante con ID " << id << std::endl;
        return false;
    }
    std::cout << "Eliminando estudiante:" << std::endl;
    std::cout << "   " << *estudiantes[indice] << std::endl;
    delete estudiantes[indice];
    for (int i = indice; i < cantidad - 1; i++) {
        estudiantes[i] = estudiantes[i + 1];
    }
    cantidad--;
    estudiantes[cantidad] = nullptr;
    std::cout << "Estudiante eliminado. Total: " << cantidad << "/" << CAPACIDAD << std::endl;
    return true;
}

bool RegistroEstudiantes::verificarIntegridad() const {
    for (int i = 0; i < cantidad; i++) {
        if (estudiantes[i] == nullptr) {
            std::cout << "Error: Espacio nulo en posicion " << i << std::endl;
            return false;
        }
    }
    std::cout << "El arreglo es contiguo y no tiene espacios intermedios." << std::endl;
    return true;
}