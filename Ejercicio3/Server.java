package Ejercicio3;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 35000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado en el puerto " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine = in.readLine();
                System.out.println("Número recibido: " + inputLine);

                try {
                    int numero = Integer.parseInt(inputLine);
                    int cuadrado = numero * numero;

                    out.println("Respuesta: " + cuadrado);
                } catch (NumberFormatException e) {
                    out.println("Error: el valor recibido no es un número válido.");
                }

                clientSocket.close();
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
