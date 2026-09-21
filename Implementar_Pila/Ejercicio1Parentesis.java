package Implementar_Pila;

import java.util.ArrayDeque;
import java.util.Deque;

public class Ejercicio1Parentesis {

    public static void main(String[] args) {
        String entrada = "([{}])";
        Deque<Character> pila = new ArrayDeque<>();
        boolean esValida = true;

        for (char c : entrada.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                pila.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (pila.isEmpty()) {
                    esValida = false;
                    break;
                }
                char cima = pila.pop();
                if ((c == ')' && cima != '(') ||
                    (c == ']' && cima != '[') ||
                    (c == '}' && cima != '{')) {
                    esValida = false;
                    break;
                }
            }
        }

        if (!pila.isEmpty()) {
            esValida = false;
        }

        if (esValida) {
            System.out.println("Resultado: expresión válida");
        } else {
            System.out.println("Resultado: expresión no válida");
        }
    }
}