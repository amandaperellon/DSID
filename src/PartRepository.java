import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class PartRepository extends UnicastRemoteObject implements IPartRepository {
    private final LinkedList<IPart> Repository;
    private final String Name;

    public PartRepository(String name) throws RemoteException{
        super();
        Repository = new LinkedList<>();
        Name = name;
    }

    @Override
    public String listAll() throws RemoteException {
        String list = "";
        if(Repository.size() > 0) {
            list += "------Lista de Peças do Repositório-------\n";
            for (IPart p : Repository) {
                list += "\n" + p.ToString() + "\n" +
                        "=======||=======\n";
            }
        }else{
            list = "Repositório vazio";
        }
        return list;
    }

    @Override
    public UUID createPart(String name, String description, HashMap<IPart, Integer> subcomponents) throws RemoteException {
        IPart part = new Part(name, description, subcomponents, Name);
        Repository.add(part);
        return part.getId();
    }

    @Override
    public IPart getPartByCode(UUID code) throws RemoteException {
        for (IPart part : Repository) {
            if (part.getId().equals(code)) {
                return part;
            }
        }
        return null;
    }

    @Override
    public int countParts() throws RemoteException {
        return Repository.size();
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String verifySubpart(IPart part) throws RemoteException {
        return part.verifySubpartType();
    }
}
