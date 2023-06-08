package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Server {

    public Server(){
        try{
            Scanner sc = new Scanner(System.in);
            System.out.print("Entre com o nome do servidor: ");
            String name = sc.nextLine();
            System.out.print("Entre com a porta do servidor: ");
            int port = Integer.parseInt(sc.nextLine());

            IPartRepository repository = new PartRepository(name);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind(name, repository);
            System.out.println("Servidor "+name+" iniciado com sucesso");
        }catch (Exception ex){
            System.out.println("Erro ao iniciar o servidor: "+ex.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
