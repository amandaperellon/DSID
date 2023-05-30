import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Part extends UnicastRemoteObject implements IPart {
    public UUID Code;
    public String Name;
    public String Description;
    public HashMap<IPart, Integer> Subcomponents;
    public String RepositoryName;

    public Part(String name, String description) throws RemoteException {
        super();
        Code = UUID.randomUUID();
        Name = name;
        Description = description;
        Subcomponents = new HashMap<>();
    }

    public Part(String name, String description, HashMap<IPart, Integer> subcomponents, String repositoryName) throws RemoteException {
        super();
        Code = UUID.randomUUID();
        Name = name;
        Description = description;
        Subcomponents = subcomponents;
        RepositoryName = repositoryName;
    }

    @Override
    public String ToString() throws RemoteException {
        String text = "Part ID: "+Code+"\nPart Name: "+Name+"\nPart Description: "+Description+"\n";
        if(Subcomponents.size() > 0){
            text += "Subcomponents:\n";
            for (Map.Entry<IPart,Integer> s : Subcomponents.entrySet()) {
                text += "Part Name: "+s.getKey().getName()+"\n";
                text += "Quantity: "+s.getValue()+"\n";
            }
        }else{
            text += "Part primitiva\n";
        }
        return text;
    }

    @Override
    public UUID getCode() throws RemoteException {
        return Code;
    }

    @Override
    public String getName() throws RemoteException {
        return Name;
    }

    @Override
    public String getDescription() throws RemoteException {
        return Description;
    }

    @Override
    public void setCode(UUID code) throws RemoteException {
        Code = code;
    }

    @Override
    public void setName(String name) throws RemoteException {
        Name = name;
    }

    @Override
    public void setDescription(String description) throws RemoteException {
        Description = description;
    }

    @Override
    public String addSubcomponent(Part part, Integer quantity) throws RemoteException {
        Subcomponents.put(part, quantity);
        return this.toString();
    }

    @Override
    public int getSubcomponetsSize() throws RemoteException {
        return Subcomponents.size();
    }

    @Override
    public String getRepositoryName() throws RemoteException {
        return RepositoryName;
    }
}
