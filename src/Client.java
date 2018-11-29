import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String args[]){

        try {
            System.out.print("Installing security manager...");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            System.out.println("done.");

            System.out.print("Getting the registry...");
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("done.");

            System.out.print("Getting the stub...");
            TicketMachine ticketMachine = (TicketMachine) registry.lookup("ticketMachine");
            System.out.println("done.");

            System.out.print("Inovking the remote method...");
            boolean ticketDispo = ticketMachine.bookTickets(3);

            System.out.println("TicketDispo? " + ticketDispo);

            System.out.print("Inovking the remote method...");
            ticketMachine.addNewTickets(3);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
