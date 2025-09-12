package Ejercicio2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MiniBrowser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la URL (ejemplo: http://www.google.com): ");
        String urlString = scanner.nextLine();

        try {
            // Creamos el objeto URL
            URL url = new URL(urlString);

            // Abrimos un flujo de lectura desde la URL
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );

            // Creamos un archivo de salida
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("Ejercicio2/resultado.html")
            );

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                writer.write(inputLine);
                writer.newLine(); // Agrega salto de línea
            }

            // Cerramos los flujos
            reader.close();
            writer.close();

            System.out.println("El contenido de la página se guardó en 'resultado.html'");
            System.out.println("Abra ese archivo en su navegador para ver la página.");

        } catch (MalformedURLException e) {
            System.err.println("La URL está mal formada: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al leer o escribir: " + e.getMessage());
        }
    }
}
