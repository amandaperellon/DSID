import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface IPart extends Remote {
    public String ToString() throws RemoteException;
    public UUID getCode() throws RemoteException;
    public String getName() throws RemoteException;
    public String getDescription() throws RemoteException;
    public void setCode(UUID code) throws RemoteException;
    public void setName(String name) throws RemoteException;
    public void setDescription(String description) throws RemoteException;
    public String addSubcomponent(Part part, Integer quantity) throws RemoteException;
    public int getSubcomponetsSize() throws RemoteException;
    public String getRepositoryName() throws RemoteException;
    public String listSubcomponents() throws RemoteException;
    public String verifySubcomponentsType() throws RemoteException;
}
