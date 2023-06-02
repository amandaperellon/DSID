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
            System.out.print("Entre com o comando: ");
            String option = sc.nextLine();
            try {
                switch (option) {
                    case "bind": {
                        startServer();
                    }break;
                    case "listp": {
                        System.out.print(repository.listAll());
                    }
                    break;
                    case "addp": {
                        System.out.print("Entre com o nome da part: ");
                        String name  = sc.nextLine();
                        System.out.print("Entre com a descricao da part: ");
                        String description = sc.nextLine();
                        UUID codigo = repository.createPart(name, description, subcomponents);
                        System.out.print("Part com codigo "+codigo+" criada com sucesso!!!");
                    }
                    break;
                    case "getp": {
                        System.out.print("Entre com o codigo da part: ");
                        String code = sc.nextLine();
                        IPart p = repository.getPartByCode(UUID.fromString(code));
                        if(p != null) {
                            part = p;
                            System.out.print(part.getName()+" se tornou a part corrente!!");
                        }else{
                            System.out.print("Não foi possivel encontrar uma part no repositorio "+repository.getName()+" com esse codigo");
                        }
                    }break;
                    case "showp": {
                        if(part != null) {
                            System.out.print(part.ToString());
                        }else{
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }break;
                    case "addsubpart":{
                        System.out.print("Entre com a quantidade que deseja adicionar: ");
                        int quantity = Integer.parseInt(sc.nextLine());
                        if(part != null) {
                            subcomponents.put(part, quantity);
                            System.out.print("Subcomponent adicionado com sucesso!!");
                        }else{
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }break;
                    case "clearlist":{
                        subcomponents.clear();
                        System.out.print("Lista de subcomponentes esvaziada com sucesso!!");
                    }break;
                    case "help":{
                        menu();
                    }break;
                    case "inforep":{
                        System.out.print("Nome do repositorio: "+repository.getName()+"\n"+
                        "Quantidade de parts: "+repository.countParts());
                    }break;
                    case "inforeppart":{
                        if(part != null) {
                            System.out.print("Nome do repositorio que se encontra a peca corrente: "+part.getRepositoryName());
                        }else{
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }break;
                    case "subpartinfo":{
                        if(part != null) {
                            System.out.print(repository.verifySubcomponents(part));
                        }else{
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }break;
                    case "countsubpart":{
                        if(part != null) {
                            System.out.print("Numero de subcomponents: "+part.getSubcomponetsSize());
                        }else{
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }break;
                    case "listsubpart":{
                        if(part != null) {
                            System.out.print(part.listSubcomponents());
                        }else{
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }break;
                    case "quit": {
                        sc.close();
                        return;
                    }
                    default:{
                        System.out.print("Nao ha um comando com esse nome, entre com o comando help que ira aparecer os comandos possiveis");
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
                "countsubpart: Numero de subcomponentes da part corrente\n"+
                "listsubpart: Lista os subcomponentes da part corrente"+
                "quit: Encerra a execucao\n");
    }
}