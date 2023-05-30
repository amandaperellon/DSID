import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class PartRepository extends UnicastRemoteObject implements IPartRepository {

    public LinkedList<IPart> _repository;
    public String Name;

    public PartRepository(String name) throws RemoteException{
        super();
        _repository = new LinkedList<>();
        Name = name;
    }

    @Override
    public String listAll() throws RemoteException {
        String list = "";
        if(_repository.size() > 0) {
            list += "------Lista de Parts do Repositorio-------\n\n";
            for (int i = 0; i < _repository.size(); i++) {
                IPart p = _repository.get(i);
                list += "\n"+p.ToString()+"\n"+
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
        for(int i=0; i<_repository.size(); i++){
            IPart part = _repository.get(i);
            if(part.getCode().equals(code)){
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
        if(part.getSubcomponetsSize() > 0){
            return "Peca agregada";
        }else{
            return "Peca primitiva";
        }
    }
}
