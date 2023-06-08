package client;

import server.IPart;
import server.IPartRepository;

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
    private static final HashMap<IPart, Integer> subpart = new HashMap<>();

    public static void main(String[] args) {
        host = (args.length < 1) ? null : args[0];
        try{
            startServer();
            menu();
            commands();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void startServer(){
        try {
            System.out.print("Entre com a ip do servidor: ");
            host = sc.nextLine();
            System.out.print("Entre com o nome do server que deseja se conectar: ");
            String repositoryName = sc.nextLine();
            System.out.print("Entre com a porta do servidor: ");
            int port = Integer.parseInt(sc.nextLine());

            Registry registry = LocateRegistry.getRegistry(host,port);
            repository = (IPartRepository) registry.lookup(repositoryName);
            System.out.println("Conectado com o servidor: " + repositoryName);
        }catch(Exception e){
            System.out.println("Não foi possível se conectar com o servidor: "+e.getMessage());
            System.exit(0);
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
                        System.out.print("Entre com o nome da peça: ");
                        String name = sc.nextLine();
                        System.out.print("Entre com a descrição da peça: ");
                        String description = sc.nextLine();
                        UUID id = repository.createPart(name, description, subpart);
                        System.out.print("Peça com id " + id + " criada com sucesso");
                    }
                    case "getp" -> {
                        System.out.print("Entre com o id da peça: ");
                        String id = sc.nextLine();
                        IPart p = repository.getPartByCode(UUID.fromString(id));
                        if (p != null) {
                            part = p;
                            System.out.print(part.getName() + " se tornou a peça corrente");
                        } else {
                            System.out.print("Não foi possível encontrar uma peça no repositório " + repository.getName() + " com esse id");
                        }
                    }
                    case "showp" -> {
                        if (part != null) {
                            System.out.print(part.ToString());
                        } else {
                            System.out.print("Não há nenhuma peça corrente nesse momento");
                        }
                    }
                    case "addsubpart" -> {
                        System.out.print("Entre com a quantidade que deseja adicionar: ");
                        int quantity = Integer.parseInt(sc.nextLine());
                        if (part != null) {
                            subpart.put(part, quantity);
                            System.out.print("Sub-peças adicionado com sucesso!!");
                        } else {
                            System.out.print("Não há nenhuma peça corrente nesse momento");
                        }
                    }
                    case "clearlist" -> {
                        subpart.clear();
                        System.out.print("Lista de Sub-peças esvaziada com sucesso!!");
                    }
                    case "help" ->
                        menu();

                    case "inforep" ->
                        System.out.print("Nome do repositório: " + repository.getName() + "\n" +
                                "Quantidade de Peças: " + repository.countParts());

                    case "inforeppart" -> {
                        if (part != null) {
                            System.out.print("Nome do repositório que se encontra a peça corrente: " + part.getRepositoryName());
                        } else {
                            System.out.print("Não há nenhuma peça corrente nesse momento");
                        }
                    }
                    case "partinfo" -> {
                        if (part != null) {
                            System.out.print(repository.verifySubpart(part));
                        } else {
                            System.out.print("Não há nenhuma peça corrente nesse momento");
                        }
                    }
                    case "countsubpart" -> {
                        if (part != null) {
                            System.out.println("Numero de sub-peças: " + part.getSubpartSize());
                            System.out.println("Número de sub-peças primitivas: "+part.getSubpartPrimitiveSize());
                            System.out.println("Número de sub-peças agregadas: "+part.getSubpartAggregatedSize());
                        } else {
                            System.out.print("Não há nenhuma peça corrente nesse momento");
                        }
                    }
                    case "listsubpart" -> {
                        if (part != null) {
                            System.out.print(part.listSubpart());
                        } else {
                            System.out.print("Não há nenhuma peça corrente nesse momento");
                        }
                    }
                    case "quit" -> {
                        sc.close();
                        return;
                    }
                    default ->
                        System.out.print("Não há um comando com esse nome, entre com o comando help para mostrar os comandos possíveis");

                }
                System.out.println("\n");
            } catch (Exception e) {
                System.out.println("Erro: "+e.getMessage());
                System.exit(0);
            }
        }
    }

    public static void menu(){
        System.out.println("""
                -------------------------------------Menu--------------------------------------
                help: Mostra os comandos possíveis
                bind: Se conectar a outro servidor e muda o repositório corrente
                listp: Lista as peças do repositório corrente
                getp: Busca uma peça por id
                showp: Mostra atributos da peça corrente
                clearlist: Esvazia a lista de sub-peças corrente
                addsubpart: Adiciona a lista de sub-peças corrente n unidades da peça corrente
                addp: Adiciona uma peça ao repositório corrente
                inforep: Mostra as informações sobre o repositório
                inforeppart: Mostra o nome do repositório que a peça corrente se encontra
                partinfo: Mostra se a peça corrente é primitiva ou agregada
                countsubpart: Número de sub-peças da peça corrente
                listsubpart: Lista os sub-peças da peça corrente
                quit: Encerra a execução
                -------------------------------------------------------------------------------
                """);
    }
}