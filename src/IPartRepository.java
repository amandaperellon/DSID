import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public interface IPartRepository extends Remote {
    public String listAll() throws RemoteException;
    public UUID createPart(String name, String description, HashMap<IPart, Integer> subcomponents) throws RemoteException;
    public IPart getPartByCode(UUID code) throws RemoteException;
    public int countParts() throws RemoteException;
    public String getName() throws RemoteException;
    public String verifySubcomponents(IPart part) throws RemoteException;
}
