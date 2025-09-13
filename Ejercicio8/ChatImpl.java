package Ejercicio8;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatImpl extends UnicastRemoteObject implements ChatInterface {

    private String nombre;

    public ChatImpl(String nombre) throws RemoteException {
        super();
        this.nombre = nombre;
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    public String getNombre() {
        return nombre;
    }
}
