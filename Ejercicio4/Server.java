package Ejercicio4;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 35000;
        String currentFunction = "cos";

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor de funciones iniciado en el puerto " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + inputLine);

                    if (inputLine.startsWith("fun:")) {
                        String func = inputLine.substring(4).trim();
                        if (func.equals("sin") || func.equals("cos") || func.equals("tan")) {
                            currentFunction = func;
                            out.println("Función cambiada a: " + currentFunction);
                        } else {
                            out.println("Función no reconocida. Use sin, cos o tan.");
                        }
                    } else {
                        try {
                            double numero = Double.parseDouble(inputLine);
                            double resultado;

                            switch (currentFunction) {
                                case "sin":
                                    resultado = Math.sin(numero);
                                    break;
                                case "cos":
                                    resultado = Math.cos(numero);
                                    break;
                                case "tan":
                                    resultado = Math.tan(numero);
                                    break;
                                default:
                                    resultado = Double.NaN;
                            }
                            out.println("Respuesta: " + resultado);
                        } catch (NumberFormatException e) {
                            out.println("Error: entrada no válida");
                        }
                    }
                }
                clientSocket.close();
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
