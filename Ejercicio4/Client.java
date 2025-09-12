package Ejercicio4;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "127.0.0.1"; // Servidor local
        int port = 35000;

        try (
                Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("Conectado al servidor de funciones.");
            System.out.println("Puede enviar números o cambiar función con 'fun:sin', 'fun:cos', 'fun:tan'.");

            while (true) {
                System.out.print("> ");
                String mensaje = scanner.nextLine();
                out.println(mensaje);

                String respuesta = in.readLine();
                if (respuesta == null) break; // Servidor cerró conexión
                System.out.println(respuesta);
            }

        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}
