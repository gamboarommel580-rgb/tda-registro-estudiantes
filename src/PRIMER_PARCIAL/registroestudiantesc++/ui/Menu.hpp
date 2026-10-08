#ifndef MENU_HPP
#define MENU_HPP

#include "../tda/RegistroEstudiantes.hpp"
#include <iostream>

class Menu {
private:
    std::istream& input;
    std::ostream& output;
    RegistroEstudiantes& registro;

    std::string leerString(const std::string& mensaje);
    std::string leerStringOpcional(const std::string& mensaje, const std::string& actual);
    long long leerLong(const std::string& mensaje);
    int leerInt(const std::string& mensaje, int min, int max);
    int leerIntOpcional(const std::string& mensaje, int actual, int min, int max);
    double leerDouble(const std::string& mensaje, double min, double max);
    double leerDoubleOpcional(const std::string& mensaje, double actual, double min, double max);

public:
    Menu(std::istream& input, std::ostream& output, RegistroEstudiantes& registro);

    void mostrar() const;
    void registrar();
    void listar() const;
    void buscar() const;
    void modificar();
    void eliminar();
    void verificar() const;
};

#endif