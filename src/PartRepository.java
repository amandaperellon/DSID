import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class PartRepository extends UnicastRemoteObject implements IPartRepository {

    private final LinkedList<IPart> _repository;
    private final String Name;

    public PartRepository(String name) throws RemoteException{
        super();
        _repository = new LinkedList<>();
        Name = name;
    }

    @Override
    public String listAll() throws RemoteException {
        String list = "";
        if(_repository.size() > 0) {
            list += "------Lista de Parts do Repositorio-------\n";
            for (IPart p : _repository) {
                list += "\n" + p.ToString() + "\n" +
                        "=======||=======\n";
            }
        }else{
            list = "Repositorio esta vazio";
        }
        return list;
    }

    @Override
    public UUID createPart(String name, String description, HashMap<IPart, Integer> subcomponents) throws RemoteException {
        IPart part = new Part(name, description, subcomponents, Name);
        _repository.add(part);
        return part.getCode();
    }

    @Override
    public IPart getPartByCode(UUID code) throws RemoteException {
        for (IPart part : _repository) {
            if (part.getCode().equals(code)) {
                return part;
            }
        }
        return null;
    }

    @Override
    public int countParts() throws RemoteException {
        return _repository.size();
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String verifySubcomponents(IPart part) throws RemoteException {
        return part.verifySubcomponentsType();
    }
}
