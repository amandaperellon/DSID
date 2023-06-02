import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IPart extends Remote {
    String ToString() throws RemoteException;
    UUID getCode() throws RemoteException;
    String getName() throws RemoteException;
    String getDescription() throws RemoteException;
    void setCode(UUID code) throws RemoteException;
    void setName(String name) throws RemoteException;
    void setDescription(String description) throws RemoteException;
    String addSubcomponent(Part part, Integer quantity) throws RemoteException;
    int getSubcomponentsSize() throws RemoteException;
    String getRepositoryName() throws RemoteException;
    String listSubcomponents() throws RemoteException;
    String verifySubcomponentsType() throws RemoteException;
}
