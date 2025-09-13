package Ejercicio8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote {
    void receiveMessage(String message) throws RemoteException;
}
