#include "ui/Menu.hpp"
#include "model/Estudiante.hpp"
#include "tda/RegistroEstudiantes.hpp"
#include <iostream>
#include <string>

void registrarEstudiantesEjemplo(RegistroEstudiantes& registro) {
    std::cout << "\nRegistrando estudiantes del curso 3B..." << std::endl;
    Estudiante* ejemplos[] = {
        new Estudiante(12345678LL, "Ana Maria Perez", 20, 8.5),
        new Estudiante(23456789LL, "Carlos Jose Gomez", 22, 7.8),
        new Estudiante(34567890LL, "Maria Fernanda Lopez", 19, 9.2),
        new Estudiante(45678901LL, "Juan Pablo Ruiz", 21, 6.5),
        new Estudiante(56789012LL, "Laura Isabel Mora", 23, 9.8),
        new Estudiante(67890123LL, "Pedro Antonio Sanchez", 20, 7.5),
        new Estudiante(78901234LL, "Sofia Elena Ramirez", 18, 8.9),
        new Estudiante(89012345LL, "Miguel Angel Torres", 22, 6.8),
        new Estudiante(90123456LL, "Elena Patricia Vega", 21, 9.5),
        new Estudiante(10234567LL, "Jorge Luis Mendoza", 20, 7.2)
    };
    int total = sizeof(ejemplos) / sizeof(ejemplos[0]);
    for (int i = 0; i < total; i++) {
        registro.registrar(ejemplos[i]);
    }
}

void ejecutarPruebas(RegistroEstudiantes& registro) {
    std::cout << "\n" << std::string(80, '=') << std::endl;
    std::cout << "EJECUTANDO PRUEBAS OBLIGATORIAS" << std::endl;
    std::cout << std::string(80, '=') << std::endl;

    std::cout << "\n1. Intentando registrar ID repetido..." << std::endl;
    try {
        Estudiante* duplicado = new Estudiante(12345678LL, "Test Duplicado", 20, 8.0);
        registro.registrar(duplicado);
    } catch (const std::invalid_argument& e) {
        std::cout << "Error: " << e.what() << std::endl;
    }

    std::cout << "\n2. Buscando estudiante existente e inexistente..." << std::endl;
    std::cout << "Buscando ID 45678901:" << std::endl;
    Estudiante* encontrado = registro.buscarPorId(45678901LL);
    std::cout << (encontrado != nullptr ? "Encontrado: " + std::string(typeid(*encontrado).name()) : "No encontrado") << std::endl;
    std::cout << "Buscando ID 99999999:" << std::endl;
    encontrado = registro.buscarPorId(99999999LL);
    std::cout << (encontrado != nullptr ? "Encontrado" : "No encontrado") << std::endl;

    std::cout << "\n3. Modificando promedio del estudiante ID 23456789..." << std::endl;
    Estudiante* est = registro.buscarPorId(23456789LL);
    if (est != nullptr) {
        std::cout << "Antes: " << *est << std::endl;
        registro.modificar(23456789LL, est->getNombre(), est->getEdad(), 9.5);
        std::cout << "Despues: " << *registro.buscarPorId(23456789LL) << std::endl;
    }

    std::cout << "\n4. Eliminando estudiante de la mitad..." << std::endl;
    int mitad = registro.getCantidad() / 2;
    Estudiante** todos = registro.obtenerTodos();
    long long idEliminar = todos[mitad]->getId();
    std::cout << "Eliminando ID " << idEliminar << " (posicion " << mitad << ")" << std::endl;
    registro.eliminar(idEliminar);
    delete[] todos;

    std::cout << "\n5. Verificando integridad del arreglo..." << std::endl;
    registro.verificarIntegridad();

    std::cout << "\n6. Estado final del arreglo:" << std::endl;
    registro.listarTodos();
}

int main() {
    std::cout << std::string(80, '=') << std::endl;
    std::cout << "SISTEMA DE REGISTRO DE ESTUDIANTES - CURSO 3B" << std::endl;
    std::cout << "TDA Estatico - Capacidad: 30 estudiantes" << std::endl;
    std::cout << std::string(80, '=') << std::endl;

    RegistroEstudiantes registro;
    Menu menu(std::cin, std::cout, registro);

    registrarEstudiantesEjemplo(registro);
    ejecutarPruebas(registro);

    int opcion;
    do {
        menu.mostrar();
        std::string input;
        std::getline(std::cin, input);
        try {
            opcion = std::stoi(input);
        } catch (...) {
            opcion = -1;
        }
        switch (opcion) {
            case 1: menu.registrar(); break;
            case 2: menu.listar(); break;
            case 3: menu.buscar(); break;
            case 4: menu.modificar(); break;
            case 5: menu.eliminar(); break;
            case 6: menu.verificar(); break;
            case 0: break;
            default: std::cout << "Opcion invalida." << std::endl;
        }
    } while (opcion != 0);

    std::cout << "\nGracias por usar el sistema!" << std::endl;
    return 0;
}