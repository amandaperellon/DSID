import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IPart extends Remote {
    String ToString() throws RemoteException;
    UUID getId() throws RemoteException;
    String getName() throws RemoteException;
    String getDescription() throws RemoteException;
    void setId(UUID id) throws RemoteException;
    void setName(String name) throws RemoteException;
    void setDescription(String description) throws RemoteException;
    String addSubpart(Part part, Integer quantity) throws RemoteException;
    int getSubpartSize() throws RemoteException;
    String getRepositoryName() throws RemoteException;
    String listSubpart() throws RemoteException;
    String verifySubpartType() throws RemoteException;
}
