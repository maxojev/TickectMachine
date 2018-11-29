import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TicketMachineImpl implements TicketMachine {

    private int nbTicketsDispo = 6;

    @Override
    public synchronized boolean  bookTickets(int nb) throws RemoteException, InterruptedException {

        while (nb > nbTicketsDispo){
          System.out.println("Attente");
          wait();
        }
        nbTicketsDispo=nbTicketsDispo-nb;
        return true;
    }

    @Override
    public synchronized void addNewTickets(int nb) throws RemoteException {

        System.out.println("Ticket Disponible Avant" + nbTicketsDispo);
//        if (nbTicketsDispo ==0)
            nbTicketsDispo += nb;
            System.out.println("Ticket Disponible Apres" + nbTicketsDispo);
            notify();
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
