import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Part extends UnicastRemoteObject implements IPart {
    private UUID Id;
    private String Name;
    private String Description;
    private final HashMap<IPart, Integer> Subpart;
    private String RepositoryName;

    public Part(String name, String description, HashMap<IPart, Integer> subpart, String repositoryName) throws RemoteException {
        super();
        Id = UUID.randomUUID();
        Name = name;
        Description = description;
        Subpart = subpart;
        RepositoryName = repositoryName;
    }

    @Override
    public String ToString() throws RemoteException {
        String text = "Id: "+Id+
                "\nNome: "+Name+
                "\nDescrição: "+Description+
                "\n"+
                listSubpart();
        return text;
    }

    @Override
    public UUID getId() throws RemoteException {
        return Id;
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
    public void setId(UUID id) throws RemoteException {
        Id = id;
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
    public String addSubpart(Part part, Integer quantity) throws RemoteException {
        Subpart.put(part, quantity);
        return this.toString();
    }

    @Override
    public int getSubpartSize() throws RemoteException {
        return Subpart.size();
    }

    @Override
    public String getRepositoryName() throws RemoteException {
        return RepositoryName;
    }

    @Override
    public String listSubpart() throws RemoteException {
        String text = "";
        if(Subpart.size() > 0){
            text += "Sub-peça:\n";
            for (Map.Entry<IPart,Integer> s : Subpart.entrySet()) {
                text += s.getKey().toString()+"\n"+
                        "Quantidade: "+s.getValue()+"\n"+
                        verifySubpartType()+"\n----------\n";
            }
        }else{
            text += "Sub-peça: Primitiva\n";
        }
        return text;
    }

    @Override
    public String verifySubpartType() throws RemoteException {
        if (Subpart.size() > 0) {
            return "Peça Agregada";
        }else {
            return "Peça Primitiva";
        }
    }
}
