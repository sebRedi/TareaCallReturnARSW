package Ejercicio6;

import java.io.*;
import java.net.*;
import java.nio.file.*;

public class WebServer {
    public static void main(String[] args) {
        int port = 35000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor web iniciado en http://localhost:" + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream();

                String requestLine = in.readLine();
                if (requestLine == null) {
                    clientSocket.close();
                    continue;
                }

                System.out.println("Solicitud: " + requestLine);

                String[] parts = requestLine.split(" ");
                if (parts.length < 2) {
                    clientSocket.close();
                    continue;
                }

                String path = parts[1];
                if (path.equals("/")) {
                    path = "/index.html";
                }

                File file = new File("." + path);

                if (file.exists() && !file.isDirectory()) {
                    String contentType = Files.probeContentType(file.toPath());
                    if (contentType == null) {
                        contentType = "application/octet-stream";
                    }

                    byte[] fileData = Files.readAllBytes(file.toPath());
                    String header = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: " + contentType + "\r\n" +
                            "Content-Length: " + fileData.length + "\r\n" +
                            "\r\n";

                    out.write(header.getBytes());
                    out.write(fileData);

                } else {
                    String errorMessage = "<h1>404 - Not Found</h1>";
                    String header = "HTTP/1.1 404 Not Found\r\n" +
                            "Content-Type: text/html\r\n" +
                            "Content-Length: " + errorMessage.length() + "\r\n" +
                            "\r\n";
                    out.write(header.getBytes());
                    out.write(errorMessage.getBytes());
                }

                out.flush();
                clientSocket.close();
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
