import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Client {
    private static IPartRepository repository;
    private static final Scanner sc = new Scanner(System.in);
    private static String host;
    private static IPart part;
    private static final HashMap<IPart, Integer> subcomponents = new HashMap<>();

    public static void main(String[] args) {
        host = (args.length < 1) ? null : args[0];
        try{
            startServer();
            menu();
            commands();
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
                    case "bind" ->
                        startServer();

                    case "listp" ->
                        System.out.print(repository.listAll());

                    case "addp" -> {
                        System.out.print("Entre com o nome da part: ");
                        String name = sc.nextLine();
                        System.out.print("Entre com a descricao da part: ");
                        String description = sc.nextLine();
                        UUID codigo = repository.createPart(name, description, subcomponents);
                        System.out.print("Part com codigo " + codigo + " criada com sucesso!!!");
                    }
                    case "getp" -> {
                        System.out.print("Entre com o codigo da part: ");
                        String code = sc.nextLine();
                        IPart p = repository.getPartByCode(UUID.fromString(code));
                        if (p != null) {
                            part = p;
                            System.out.print(part.getName() + " se tornou a part corrente!!");
                        } else {
                            System.out.print("Não foi possivel encontrar uma part no repositorio " + repository.getName() + " com esse codigo");
                        }
                    }
                    case "showp" -> {
                        if (part != null) {
                            System.out.print(part.ToString());
                        } else {
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }
                    case "addsubpart" -> {
                        System.out.print("Entre com a quantidade que deseja adicionar: ");
                        int quantity = Integer.parseInt(sc.nextLine());
                        if (part != null) {
                            subcomponents.put(part, quantity);
                            System.out.print("Subcomponent adicionado com sucesso!!");
                        } else {
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }
                    case "clearlist" -> {
                        subcomponents.clear();
                        System.out.print("Lista de subcomponentes esvaziada com sucesso!!");
                    }
                    case "help" ->
                        menu();

                    case "inforep" ->
                        System.out.print("Nome do repositorio: " + repository.getName() + "\n" +
                                "Quantidade de parts: " + repository.countParts());

                    case "inforeppart" -> {
                        if (part != null) {
                            System.out.print("Nome do repositorio que se encontra a peca corrente: " + part.getRepositoryName());
                        } else {
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }
                    case "subpartinfo" -> {
                        if (part != null) {
                            System.out.print(repository.verifySubcomponents(part));
                        } else {
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }
                    case "countsubpart" -> {
                        if (part != null) {
                            System.out.print("Numero de subcomponents: " + part.getSubcomponentsSize());
                        } else {
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }
                    case "listsubpart" -> {
                        if (part != null) {
                            System.out.print(part.listSubcomponents());
                        } else {
                            System.out.print("Nao ha nenhuma part corrente nesse momento");
                        }
                    }
                    case "quit" -> {
                        sc.close();
                        return;
                    }
                    default ->
                        System.out.print("Nao ha um comando com esse nome, entre com o comando help que ira aparecer os comandos possiveis");

                }
                System.out.println("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void menu(){
        System.out.println("""
                -------COMANDS-------
                help: mostra os comandos possiveis
                bind: Se conectar a outro servidor e muda o repositorio corrente
                listp: Lista as pecas do repositorio corrente
                getp: Busca uma peca por codigo
                showp: Mostra atributos da peca corrente
                clearlist: Esvazia a lista de sub-pecas corrente
                addsubpart: Adiciona a lista de sub-pecas corrente n unidades da peca corrente
                addp: Adiciona uma peca ao repositorio corrente
                inforep: Mostra as informacoes sobre o repositorio
                inforeppart: Mostra o nome do repositorio que a peca corrente se encontra
                subpartinfo: Mostra se a peca corrente é primitiva ou agregada
                countsubpart: Numero de subcomponentes da part corrente
                listsubpart: Lista os subcomponentes da part correntequit: Encerra a execucao
                """);
    }
}