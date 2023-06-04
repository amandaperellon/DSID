package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IPart extends Remote {
    String ToString() throws RemoteException;
    UUID getId() throws RemoteException;
    String getName() throws RemoteException;
    String getDescription() throws RemoteException;
    int getSubpartSize() throws RemoteException;
    String getRepositoryName() throws RemoteException;
    String listSubpart() throws RemoteException;
    String verifySubpartType() throws RemoteException;
    int getSubpartPrimitiveSize() throws RemoteException;
    int getSubpartAggregatedSize() throws RemoteException;
}
