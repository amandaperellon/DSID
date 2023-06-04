package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
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
            LocateRegistry.createRegistry(port);
            Naming.rebind("rmi://localhost/"+name, repository);
            System.out.println("Servidor "+name+" iniciado com sucesso");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
