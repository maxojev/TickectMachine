import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TicketMachineImpl implements TicketMachine {

    private int nbTicketsDispo;

    @Override
    public boolean bookTickets(int nb) {

        if(nb <= nbTicketsDispo){
            return true;
        } else{
            return false;
        }

    }

    @Override
    public void addNewTickets(int nb) {

        System.out.println("Ticket Disponible Avant" + nbTicketsDispo);

        nbTicketsDispo += nb;

        System.out.println("Ticket Disponible Après" + nbTicketsDispo);

    }

    public static void main(String[] args){

        try {
            System.out.print("Installing security manager...");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            System.out.println("done.");

            System.out.print("Creating the registry of RMI services...");
            LocateRegistry.createRegistry(1099);
            System.out.println("done.");

            System.out.print("Creating the remotely accessible TicketMachineImpl(and the stub)...");
            TicketMachine ticketMachine = new TicketMachineImpl();
            TicketMachine stub = (TicketMachine) UnicastRemoteObject.exportObject(ticketMachine, 0);
            System.out.println("done.");

            System.out.println("Registering the stub...");
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ticketMachine", stub);
            System.out.println("done.");
        }
        catch(Exception re){
            re.printStackTrace();
        }
    }
}
