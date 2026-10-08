#include "Menu.hpp"
#include "../model/Estudiante.hpp"
#include <iomanip>
#include <limits>
#include <stdexcept>

Menu::Menu(std::istream& input, std::ostream& output, RegistroEstudiantes& registro)
    : input(input), output(output), registro(registro) {}

void Menu::mostrar() const {
    output << "\n" << std::string(80, '=') << std::endl;
    output << "SISTEMA DE REGISTRO DE ESTUDIANTES - CURSO 3B" << std::endl;
    output << "Capacidad: " << registro.getCapacidad() << " estudiantes" << std::endl;
    output << std::string(80, '=') << std::endl;
    output << "1. Registrar estudiante" << std::endl;
    output << "2. Listar estudiantes" << std::endl;
    output << "3. Buscar estudiante por ID" << std::endl;
    output << "4. Modificar estudiante" << std::endl;
    output << "5. Eliminar estudiante" << std::endl;
    output << "6. Verificar integridad del arreglo" << std::endl;
    output << "0. Salir" << std::endl;
    output << std::string(80, '=') << std::endl;
    output << "Seleccione una opcion: ";
}

void Menu::registrar() {
    if (registro.estaLleno()) {
        output << "Error: Capacidad maxima alcanzada." << std::endl;
        return;
    }
    output << "\n--- REGISTRAR ESTUDIANTE ---" << std::endl;
    try {
        long long id = leerLong("ID (8-10 digitos): ");
        if (registro.buscarPorId(id) != nullptr) {
            output << "Error: ID ya registrado." << std::endl;
            return;
        }
        std::string nombre = leerString("Nombre: ");
        int edad = leerInt("Edad (15-100): ", 15, 100);
        double promedio = leerDouble("Promedio (0.0-10.0): ", 0.0, 10.0);
        Estudiante* nuevo = new Estudiante(id, nombre, edad, promedio);
        registro.registrar(nuevo);
    } catch (const std::invalid_argument& e) {
        output << "Error: " << e.what() << std::endl;
    }
}

void Menu::listar() const {
    registro.listarTodos();
}

void Menu::buscar() const {
    if (registro.estaVacio()) {
        output << "El registro esta vacio." << std::endl;
        return;
    }
    long long id = leerLong("ID a buscar: ");
    Estudiante* est = registro.buscarPorId(id);
    if (est != nullptr) {
        output << "Estudiante encontrado:" << std::endl;
        output << *est << std::endl;
    } else {
        output << "No encontrado." << std::endl;
    }
}

void Menu::modificar() {
    if (registro.estaVacio()) {
        output << "El registro esta vacio." << std::endl;
        return;
    }
    long long id = leerLong("ID a modificar: ");
    Estudiante* est = registro.buscarPorId(id);
    if (est == nullptr) {
        output << "No encontrado." << std::endl;
        return;
    }
    output << "Datos actuales: " << *est << std::endl;
    output << "\nIngrese nuevos datos (Enter para mantener):" << std::endl;
    std::string nombre = leerStringOpcional("Nuevo nombre: ", est->getNombre());
    int edad = leerIntOpcional("Nueva edad (15-100): ", est->getEdad(), 15, 100);
    double promedio = leerDoubleOpcional("Nuevo promedio (0.0-10.0): ", est->getPromedio(), 0.0, 10.0);
    registro.modificar(id, nombre, edad, promedio);
}

void Menu::eliminar() {
    if (registro.estaVacio()) {
        output << "El registro esta vacio." << std::endl;
        return;
    }
    long long id = leerLong("ID a eliminar: ");
    Estudiante* est = registro.buscarPorId(id);
    if (est == nullptr) {
        output << "No encontrado." << std::endl;
        return;
    }
    output << "Estudiante a eliminar: " << *est << std::endl;
    output << "Confirmar (s/N): ";
    std::string confirm;
    std::getline(input, confirm);
    if (confirm == "s" || confirm == "si") {
        registro.eliminar(id);
    } else {
        output << "Operacion cancelada." << std::endl;
    }
}

void Menu::verificar() const {
    registro.verificarIntegridad();
}

std::string Menu::leerString(const std::string& mensaje) {
    while (true) {
        output << mensaje;
        std::string input;
        std::getline(this->input, input);
        if (!input.empty()) return input;
        output << "El campo no puede estar vacio." << std::endl;
    }
}

std::string Menu::leerStringOpcional(const std::string& mensaje, const std::string& actual) {
    output << mensaje;
    std::string input;
    std::getline(this->input, input);
    return input.empty() ? actual : input;
}

long long Menu::leerLong(const std::string& mensaje) {
    while (true) {
        try {
            output << mensaje;
            std::string input;
            std::getline(this->input, input);
            if (input.empty()) {
                output << "El ID no puede estar vacio." << std::endl;
                continue;
            }
            long long valor = std::stoll(input);
            if (valor >= 10000000LL && valor <= 9999999999LL) return valor;
            output << "El ID debe tener entre 8 y 10 digitos." << std::endl;
        } catch (const std::exception&) {
            output << "Debe ingresar un numero valido." << std::endl;
        }
    }
}

int Menu::leerInt(const std::string& mensaje, int min, int max) {
    while (true) {
        try {
            output << mensaje;
            std::string input;
            std::getline(this->input, input);
            if (input.empty()) {
                output << "El campo no puede estar vacio." << std::endl;
                continue;
            }
            int valor = std::stoi(input);
            if (valor >= min && valor <= max) return valor;
            output << "El valor debe estar entre " << min << " y " << max << "." << std::endl;
        } catch (const std::exception&) {
            output << "Debe ingresar un numero entero." << std::endl;
        }
    }
}

int Menu::leerIntOpcional(const std::string& mensaje, int actual, int min, int max) {
    while (true) {
        output << mensaje;
        std::string input;
        std::getline(this->input, input);
        if (input.empty()) return actual;
        try {
            int valor = std::stoi(input);
            if (valor >= min && valor <= max) return valor;
            output << "El valor debe estar entre " << min << " y " << max << "." << std::endl;
        } catch (const std::exception&) {
            output << "Debe ingresar un numero entero." << std::endl;
        }
    }
}

double Menu::leerDouble(const std::string& mensaje, double min, double max) {
    while (true) {
        try {
            output << mensaje;
            std::string input;
            std::getline(this->input, input);
            if (input.empty()) {
                output << "El campo no puede estar vacio." << std::endl;
                continue;
            }
            double valor = std::stod(input);
            if (valor >= min && valor <= max) return valor;
            output << "El valor debe estar entre " << min << " y " << max << "." << std::endl;
        } catch (const std::exception&) {
            output << "Debe ingresar un numero decimal." << std::endl;
        }
    }
}

double Menu::leerDoubleOpcional(const std::string& mensaje, double actual, double min, double max) {
    while (true) {
        output << mensaje;
        std::string input;
        std::getline(this->input, input);
        if (input.empty()) return actual;
        try {
            double valor = std::stod(input);
            if (valor >= min && valor <= max) return valor;
            output << "El valor debe estar entre " << min << " y " << max << "." << std::endl;
        } catch (const std::exception&) {
            output << "Debe ingresar un numero decimal." << std::endl;
        }
    }
}