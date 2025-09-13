package Ejercicio7;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UDPServer {

    public static void main(String[] args) {
        int puerto = 9999;

        try {
            DatagramSocket socket = new DatagramSocket(puerto);
            System.out.println("Servidor UDP iniciado en el puerto " + puerto);

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socket.receive(peticion);

                String mensaje = new String(peticion.getData(), 0, peticion.getLength());
                System.out.println("Mensaje recibido: " + mensaje);

                if (mensaje.equalsIgnoreCase("hora")) {
                    String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    InetAddress direccion = peticion.getAddress();
                    int puertoCliente = peticion.getPort();

                    byte[] respuesta = hora.getBytes();
                    DatagramPacket paqueteRespuesta = new DatagramPacket(
                            respuesta, respuesta.length, direccion, puertoCliente);

                    socket.send(paqueteRespuesta);
                    System.out.println("Hora enviada: " + hora);
                }
            }
        } catch (Exception e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
