import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Client {
    public static IPartRepository repository;
    public static Scanner sc = new Scanner(System.in);
    public static String host;
    public static IPart part;
    public static HashMap<IPart, Integer> subcomponents = new HashMap<>();

    public static void main(String[] args) {
        host = (args.length < 1) ? null : args[0];
        try{
            startServer();
            menu();
            commands();
            //System.out.println(repository.createPart("teste", "teste2"));
            //System.out.println(repository.createPart("teste3", "teste4"));
            //System.out.println(repository.listAll());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void startServer(){
        try {
            System.out.println("Entre com o nome do server que deseja se conectar");
            String repositoryName = sc.nextLine();

            Registry registry = LocateRegistry.getRegistry(host);
            repository = (IPartRepository) registry.lookup(repositoryName);
            System.out.println("Conectado com o servidor: " + repositoryName);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void commands() {
        while(true) {
            System.out.println("Entre com o comando");
            String option = sc.nextLine();
            try {
                switch (option) {
                    case "bind": {
                        startServer();
                    }break;
                    case "listp": {
                        System.out.println(repository.listAll());
                    }
                    break;
                    case "addp": {
                        System.out.println("Entre com o nome da part");
                        String name  = sc.nextLine();
                        System.out.println("Entre com a descricao da part");
                        String description = sc.nextLine();
                        UUID codigo = repository.createPart(name, description, subcomponents);
                        System.out.println("Part com codigo "+codigo+" criada com sucesso!!!");
                    }
                    break;
                    case "getp": {
                        System.out.println("Entre com o codigo da part");
                        String code = sc.nextLine();
                        part = repository.getPartByCode(UUID.fromString(code));
                        if(part != null) {
                            System.out.println(part.getName()+" se tornou a part corrente!!");
                        }else{
                            System.out.println("Não foi possivel encontrar uma part no repositorio "+repository.getName()+" com esse codigo");
                        }
                    }break;
                    case "showp": {
                        if(part != null) {
                            System.out.println(part.ToString());
                        }else{
                            System.out.println("Nao ha nenhuma part corrente nesse momento");
                        }
                    }break;
                    case "addsubpart":{
                        System.out.println("Entre com a quantidade que deseja adicionar");
                        int quantity = Integer.parseInt(sc.nextLine());
                        if(part != null) {
                            subcomponents.put(part, quantity);
                            System.out.println("Subcomponent adicionado com sucesso!!");
                        }else{
                            System.out.println("Nao ha nenhuma part corrente nesse momento");
                        }
                    }break;
                    case "clearlist":{
                        subcomponents.clear();
                        System.out.println("Lista de subcomponentes envaziada com sucesso!!");
                    }break;
                    case "help":{
                        menu();
                    }break;
                    case "inforep":{
                        System.out.println("Nome do repositorio: "+repository.getName()+"\n"+
                        "Quantidade de parts: "+repository.countParts());
                    }break;
                    case "inforeppart":{
                        if(part != null) {
                            System.out.println("Nome do repositorio que se encontra a peca corrente: "+part.getRepositoryName());
                        }else{
                            System.out.println("Nao ha nenhuma part corrente nesse momento");
                        }
                    }break;
                    case "subpartinfo":{
                        if(part != null) {
                            System.out.println(repository.verifySubcomponents(part));
                        }else{
                            System.out.println("Nao ha nenhuma part corrente nesse momento");
                        }
                    }break;
                    case "quit": {
                        sc.close();
                        return;
                    }
                    default:{
                        System.out.println("Nao ha um comando com esse nome, entre com o comando help que ira aparecer os comandos possiveis");
                    }
                }
                System.out.println("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void menu(){
        System.out.println("-------COMANDS-------\n"+
                "help: mostra os comandos possiveis\n"+
                "bind: Se conectar a outro servidor e muda o repositorio corrente\n"+
                "listp: Lista as pecas do repositorio corrente\n"+
                "getp: Busca uma peca por codigo\n"+
                "showp: Mostra atributos da peca corrente\n"+
                "clearlist: Esvazia a lista de sub-pecas corrente\n"+
                "addsubpart: Adiciona a lista de sub-pecas corrente n unidades da peca corrente\n"+
                "addp: Adiciona uma peca ao repositorio corrente\n"+
                "inforep: Mostra as informacoes sobre o repositorio\n"+
                "inforeppart: Mostra o nome do repositorio que a peca corrente se encontra\n"+
                "subpartinfo: Mostra se a peca corrente é primitiva ou agregada\n"+
                "quit: Encerra a execucao\n");
    }
}