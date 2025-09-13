package Ejercicio7;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int puerto = 9999;
        String horaActual = "No disponible";

        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(3000);

            while (true) {
                try {
                    String mensaje = "hora";
                    byte[] buffer = mensaje.getBytes();

                    InetAddress direccion = InetAddress.getByName(host);
                    DatagramPacket peticion = new DatagramPacket(buffer, buffer.length, direccion, puerto);
                    socket.send(peticion);

                    byte[] bufferRespuesta = new byte[1024];
                    DatagramPacket respuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
                    socket.receive(respuesta);

                    horaActual = new String(respuesta.getData(), 0, respuesta.getLength());
                    System.out.println("Hora recibida del servidor: " + horaActual);

                } catch (java.net.SocketTimeoutException e) {
                    System.out.println("Servidor no respondió. Manteniendo: " + horaActual);
                }

                // Esperar 5 segundos antes de la siguiente consulta
                Thread.sleep(5000);
            }

        } catch (Exception e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }
    }
}
