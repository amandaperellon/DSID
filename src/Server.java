import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class Server {

    public Server(){
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Entre com o nome do server");
            String name = sc.nextLine();
            System.out.println("Entre com a porta do Server");
            int port = Integer.parseInt(sc.nextLine());

            //PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(repository, 1099);
            IPartRepository repository = new PartRepository(name);
            LocateRegistry.createRegistry(port);
            Naming.rebind("rmi://localhost/"+name, repository);
            System.out.println("Server up");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
