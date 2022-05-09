import java.util.HashMap;
import java.util.Scanner;

public class Worker extends Client{

    private Rank rank;

    public Worker(String firstName, String lastName, String userName, String password, boolean clubMember, boolean isWorker) {
        super(firstName, lastName, userName, password, clubMember, isWorker);
    }

    public Worker (Client client){
        this.setFirstName(client.getFirstName());
        this.setLastName(client.getLastName());
        this.setClubMember(client.isClubMember());
        this.setPassword(client.getPassword());
        this.setUserName(client.getUserName());
        this.setClubMember(client.isClubMember());
        this.setWorker(client.isWorker());
        this.setRank(rank);
    }


    public void chooseRank (int rankChoice) {
        switch (rankChoice) {
            case 1:
                this.setRank(Rank.REGULAR_WORKER);
                break;
            case 2:
                this.setRank(Rank.MANAGER);
                break;
            case 3:
                this.setRank(Rank.BOARD_MEMBER);
                break;
            default:
                System.out.println("choose only 1/2/3, try again: ");
                Scanner scanner = new Scanner(System.in);
                rankChoice = scanner.nextInt();
                chooseRank(rankChoice);
        }
    }

    public Worker() {
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
