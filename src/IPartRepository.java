import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.UUID;

public interface IPartRepository extends Remote {
    String listAll() throws RemoteException;
    UUID createPart(String name, String description, HashMap<IPart, Integer> subcomponents) throws RemoteException;
    IPart getPartByCode(UUID code) throws RemoteException;
    int countParts() throws RemoteException;
    String getName() throws RemoteException;
    String verifySubcomponents(IPart part) throws RemoteException;
}
