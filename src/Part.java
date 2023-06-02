import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Part extends UnicastRemoteObject implements IPart {
    private UUID Code;
    private String Name;
    private String Description;
    private final HashMap<IPart, Integer> Subcomponents;
    private String RepositoryName;

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
        text += listSubcomponents();
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
    public int getSubcomponentsSize() throws RemoteException {
        return Subcomponents.size();
    }

    @Override
    public String getRepositoryName() throws RemoteException {
        return RepositoryName;
    }

    @Override
    public String listSubcomponents() throws RemoteException {
        String text = "";
        if(Subcomponents.size() > 0){
            text += "Subcomponents:\n\n";
            for (Map.Entry<IPart,Integer> s : Subcomponents.entrySet()) {
                text += "Part Name: "+s.getKey().getName()+"\n";
                text += "Quantity: "+s.getValue()+"\n";
                text += verifySubcomponentsType()+"\n----------\n";
            }
        }else{
            text += "Part primitiva\n";
        }
        return text;
    }

    @Override
    public String verifySubcomponentsType() throws RemoteException {
        if(Subcomponents.size() > 0){
            return "Part agregada";
        }else{
            return "Part primitiva";
        }
    }
}
