package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Part extends UnicastRemoteObject implements IPart {
    private final UUID Id;
    private final String Name;
    private final String Description;
    private final HashMap<IPart, Integer> Subpart;
    private final String RepositoryName;

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
        return "Id: "+Id+
                "\nNome: "+Name+
                "\nDescrição: "+Description+
                "\n"+
                listSubpart();
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
    public int getSubpartSize() throws RemoteException {
        return Subpart.size();
    }

    @Override
    public String getRepositoryName() throws RemoteException {
        return RepositoryName;
    }

    @Override
    public String listSubpart() throws RemoteException {
        if(Subpart.size() > 0){
            String text = "Sub-peça:\n";
            for (Map.Entry<IPart,Integer> subpart : Subpart.entrySet()) {
                text = text.concat("Id: "+subpart.getKey().getId()+
                        "\nNome: "+subpart.getKey().getName()+
                        "\nDescrição: "+subpart.getKey().getDescription()+
                        "\nQuantidade: "+subpart.getValue()+"\n"+
                        subpart.getKey().verifySubpartType()+
                        "\n----------------------------------------\n");
            }
            return text;
        }else{
           return "Sub-peça: Primitiva\n";
        }
    }

    @Override
    public String verifySubpartType() throws RemoteException {
        if (Subpart.size() > 0) {
            return "Peça Agregada";
        }else {
            return "Peça Primitiva";
        }
    }

    @Override
    public int getSubpartPrimitiveSize() throws RemoteException {
        int count = 0;
        for (Map.Entry<IPart,Integer> subpart : Subpart.entrySet()) {
            if(subpart.getKey().getSubpartSize() == 0){
                count++;
            }
        }
        return count;
    }

    @Override
    public int getSubpartAggregatedSize() throws RemoteException {
        int count = 0;
        for (Map.Entry<IPart,Integer> subpart : Subpart.entrySet()) {
            if(subpart.getKey().getSubpartSize() > 0){
                count++;
            }
        }
        return count;
    }
}
