import java.rmi.Remote;

public interface TicketMachine extends Remote {

    public boolean bookTickets(int nb);
    public void addNewTickets(int nb);
}
