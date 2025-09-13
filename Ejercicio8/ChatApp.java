package Ejercicio8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Ingrese su nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Ingrese el puerto en el que desea escuchar: ");
            int puertoLocal = sc.nextInt();
            sc.nextLine();

            ChatImpl chatLocal = new ChatImpl(nombre);

            Registry registryLocal = LocateRegistry.createRegistry(puertoLocal);
            registryLocal.rebind("ChatService", chatLocal);

            System.out.println("Servidor de chat iniciado en el puerto " + puertoLocal);

            System.out.print("¿Desea conectarse a otro chat? (s/n): ");
            String opcion = sc.nextLine();

            ChatInterface chatRemoto = null;

            if (opcion.equalsIgnoreCase("s")) {
                System.out.print("Ingrese la IP del otro servidor: ");
                String ip = sc.nextLine();

                System.out.print("Ingrese el puerto del otro servidor: ");
                int puertoRemoto = sc.nextInt();
                sc.nextLine();

                Registry registryRemoto = LocateRegistry.getRegistry(ip, puertoRemoto);
                chatRemoto = (ChatInterface) registryRemoto.lookup("ChatService");

                System.out.println("Conectado con el servidor remoto en " + ip + ":" + puertoRemoto);
            }

            System.out.println("Escriba mensajes para enviar (\"salir\" para terminar):");

            while (true) {
                String mensaje = sc.nextLine();
                if (mensaje.equalsIgnoreCase("salir")) break;

                if (chatRemoto != null) {
                    chatRemoto.receiveMessage(nombre + ": " + mensaje);
                }
                System.out.println("Yo: " + mensaje);
            }

            System.out.println("Chat finalizado.");

        } catch (Exception e) {
            System.err.println("Error en ChatApp: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
