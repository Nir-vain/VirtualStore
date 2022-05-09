import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static final int CLIENT = 1;
    public static final int WORKER = 2;
    public static final int ALSO_WORKER = 1;
    public static final int ONLY_CLIENT = 2;
    public static final int AVAILABLE = 1;
    public static final int NOT_AVAILABLE = 2;
    public static final int REGULAR_WORKER_DISCOUNT_PERCENTAGE = 10;
    public static final int MANAGER_DISCOUNT_PERCENTAGE = 20;
    public static final int BOARD_MEMBER_DISCOUNT_PERCENTAGE = 30;


    private ArrayList<Client> users = new ArrayList<>();
    private final ArrayList<Product> products = new ArrayList<>();
    private final ArrayList<Product> availableProducts = new ArrayList<>();


    public Store(ArrayList<Client> users) {
        this.users = users;
    }

    public Store() {
    }

    public void start() {
        boolean showMenu = true;
        do {
            System.out.println("Welcome to VirtualStore, choose one of the following:\n" +
                    "1. Create new account\n" +
                    "2. Log in\n" +
                    "3. Exit");
            Scanner scanner = new Scanner(System.in);
            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    openNewAccount();
                    break;
                case 2:
                    logIn();
                    break;
                case 3:
                    showMenu = false;
                    break;
            }
        } while (showMenu);
    }



    public void openNewAccount() {
        Scanner scanner = new Scanner(System.in);
        Scanner s = new Scanner(System.in);

        System.out.println("Are you a Client or a Worker?\n1. Client \n2. Worker");
        int typeOfUserChoice = scanner.nextInt();

        String firstName;
        do {
            System.out.println("enter your first name: ");
            firstName = s.nextLine();
        } while (!isValidName(firstName));
        //client.setFirstName(firstName);
        String lastName;
        do {
            System.out.println("enter your last name: ");
            lastName = s.nextLine();
        } while (!isValidName(lastName));
        //client.setLastName(lastName);
        String userName;
        do {
            System.out.println("enter your user name: ");
            userName = s.nextLine();
        } while (!isFreeUserName(userName));
        //client.setUserName(userName);
        String password;
        do {
            System.out.println("enter your password (it must have at least 6 chars): ");
            password = s.nextLine();
        } while (!isValidPassword(password));
        switch (typeOfUserChoice) {
            case CLIENT:
                boolean clubMember = false;
                int membershipChoice;
                do {
                    System.out.println("are you a club member?\n 1- yes\n 2- no");
                    membershipChoice = scanner.nextInt();
                    if (membershipChoice == 1) {
                        clubMember = true;
                    }
                } while (membershipChoice != 1 && membershipChoice != 2);

                System.out.println("Are you also a worker?\n 1- yes\n 2- no");
                int isWorkerChoice = scanner.nextInt();
                switch (isWorkerChoice) {
                    case ALSO_WORKER:
                        Worker worker = new Worker(firstName, lastName, userName, password, clubMember, true);
                        System.out.println("what is your rank?\n 1- regular worker\n 2- manager\n 3- board member");
                        int rankChoice = scanner.nextInt();
                        worker.chooseRank(rankChoice);
                        users.add(worker);
                        break;
                    case ONLY_CLIENT:
                        Client client = new Client(firstName, lastName, userName, password, clubMember, false);
                        users.add(client);
                        for (Product product: client.getCart()) { // init current user's cart
                            client.getCart().remove(product);
                            product.initAmount();
                        }
                        break;
                }
                thanksMassage();
                start();
            case WORKER:
                Worker worker = new Worker(firstName, lastName, userName, password, false, true);
                System.out.println("what is your rank?\n 1- regular worker\n 2- manager\n 3- board member");
                int rankChoice = scanner.nextInt();
                worker.chooseRank(rankChoice);
                users.add(worker);
                for (Product product: worker.getCart()) { // init current user's cart
                    worker.getCart().remove(product);
                    product.initAmount();
                }
                break;
        }
        thanksMassage();
    }

    public void logIn() {
        Scanner scanner = new Scanner(System.in);
        Scanner s = new Scanner(System.in);
        System.out.println("are you a client or a worker?\n 1- client\n 2- worker");
        int typeOfUserChoice = scanner.nextInt();
        System.out.println("what is your user name?");
        String userName = s.nextLine();
        System.out.println("what is your password?");
        String password = s.nextLine();
        if (isExistingUser(userName, password)) {
            int indexOfWantedUser = locateIndexOfUser(userName);
            Client currentUser = users.get(indexOfWantedUser);
            switch (typeOfUserChoice) {
                case CLIENT:
                    System.out.print("Hello, " + currentUser.getFirstName() + " " + currentUser.getLastName() + " ");
                    if (currentUser.isClubMember()) System.out.println("VIP");
                    for (Product product: currentUser.getCart()) { // init current user's cart
                        currentUser.getCart().remove(product);
                        product.initAmount();
                    }
                    clientMenu(currentUser, indexOfWantedUser);
                    break;
                case WORKER:
                    if (currentUser.isWorker()) {
                        for (Product product: currentUser.getCart()) { // init current user's cart
                            currentUser.getCart().remove(product);
                            product.initAmount();
                        }
                        workerMenu((Worker) currentUser, indexOfWantedUser);
                    } else System.out.println("The details you entered do not match any worker! Try to log in as a client");
                    start();
                    break;
                default:
                    System.out.println("invalid Choice!");
                    start();
            }
        } else System.out.println("wrong details!");
        start();
    }



    public void clientMenu (Client client, int index) {
        float workerTotalSum = 0;
        System.out.println("the products that are available: ");
        if (!(products.isEmpty())) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).isAvailable()) {
                    if (!(availableProducts.contains(products.get(i)))) {
                        availableProducts.add(products.get(i));
                    }
                }
            }
            if (availableProducts.size() == 0) {
                System.out.println("Sorry, no products are available");
                start();
            } else {
                for (int i = 0; i < availableProducts.size(); i++) {
                    System.out.println(i + "- " + availableProducts.get(i).getProductName());
                }
                System.out.println("choose the number of the product that you would like to buy:\n" +
                        "to end shopping- type '-1' ");
                Scanner scanner = new Scanner(System.in);
                int productChoice;
                do {
                    productChoice = scanner.nextInt();
                } while ((productChoice < 0 || productChoice > availableProducts.size()) && (productChoice != -1));
                if (productChoice == -1) {
                    if (!(client.isWorker())) {
                        System.out.println("Thanks for buying! your total sum is: " + client.getTotalSumOfCart());
                        users.get(index).setTotalSumOfCart(client.getTotalSumOfCart());
                        for (Product product: client.getCart()) { // init current user's cart
                            product.initAmount();
                        }

                    } else {
                        workerTotalSum = calculateWorkersBill((Worker) client);
                        System.out.println("Thanks for buying! your total sum is: " + workerTotalSum);
                        users.get(index).setTotalSumOfCart(workerTotalSum);
                        for (Product product: client.getCart()) { // init current user's cart
                            product.initAmount();
                        }

                    }
                    start();

                } else {
                    Product productToAdd =  products.get(productChoice);
                    System.out.println("How much would you like to buy from this product?");
                    int amountChoice = 0;
                    do {
                        amountChoice = scanner.nextInt();
                    } while (amountChoice < 0);
                    productToAdd.setAmount(amountChoice);
                    client.addToCart(productToAdd);
                    for (Product product: client.getCart()) {
                            System.out.println(product.getProductName() + ", amount: " + product.getAmount());
                    }
                    if (!(client.isWorker())) {
                        System.out.println("your bill is: " + client.getTotalSumOfCart());
                        } else {
                        workerTotalSum = calculateWorkersBill((Worker) client);
                        System.out.println("your bill is: " + workerTotalSum);
                        }

                    }

                    clientMenu(client, index);

                }
        } else {
            System.out.println("Sorry, no products are available");
            for (Product product: client.getCart()) { // init current user's cart
                client.getCart().remove(product);
                product.initAmount();
            }
            start();
        }
    }


    public float calculateWorkersBill (Worker currentWorker) {
        float workerTotalSum = 0;
        if (currentWorker.getRank() == Rank.REGULAR_WORKER) {
            workerTotalSum = currentWorker.getTotalSumOfCart() - (((float)currentWorker.getTotalSumOfCart() * REGULAR_WORKER_DISCOUNT_PERCENTAGE) / 100);
        } else if (currentWorker.getRank() == Rank.MANAGER) {
            workerTotalSum = currentWorker.getTotalSumOfCart() - (((float)currentWorker.getTotalSumOfCart() * MANAGER_DISCOUNT_PERCENTAGE) / 100);
        } else if (currentWorker.getRank() == Rank.BOARD_MEMBER) {
            workerTotalSum = currentWorker.getTotalSumOfCart() - (((float)currentWorker.getTotalSumOfCart() * BOARD_MEMBER_DISCOUNT_PERCENTAGE) / 100);
        }
        return workerTotalSum;
    }


    public void workerMenu(Worker currentWorker, int index) {
        System.out.println("Hello, " + currentWorker.getFirstName() + " " + currentWorker.getLastName() + " " + currentWorker.getRank());
        Scanner scanner = new Scanner(System.in);
        Scanner s = new Scanner(System.in);
        boolean showWorkerMenu = true;
        do {
            System.out.println(
                    "1- print all clients\n" +
                    "2- print all clubMembers\n" +
                    "3- print clients who have made at least one purchase\n" +
                    "4- Print the client whose purchase amount is the highest\n" +
                    "5- add new product\n" +
                    "6- change status for product\n" +
                    "7- make a purchase\n" +
                    "8- log out\n");
            int actionChoice = scanner.nextInt();
            switch (actionChoice) {
                case 1:
                    System.out.println("all clients:");
                    for (Client user : users) {
                        if (!(user.isWorker())) {
                            System.out.println(user.getFirstName() + " " + user.getLastName());
                        }
                    }
                    break;
                case 2:
                    System.out.println("club members: ");
                    for (Client user : users) {
                        if (!(user.isWorker()) && user.isClubMember()) {
                            System.out.println(user.getFirstName() + " " + user.getLastName());
                        }
                    }
                    break;
                case 3:
                    for (Client user : users) {
                        if (!(user.isWorker()) && (user.getCart().size() >= 1)) {
                            System.out.println(user.getFirstName() + " " + user.getLastName());
                        }
                    }
                    break;
                case 4:
                    Client bestBuyer = new Client();
                    for (Client user : users) {
                        if (!(user.isWorker())) {
                            if (user.getTotalSumOfCart() > bestBuyer.getTotalSumOfCart()) {
                                bestBuyer = user;
                            }
                        }
                    }
                    System.out.println(bestBuyer.getFirstName() + " " + bestBuyer.getLastName());
                    break;
                case 5:
                    System.out.println("add product's name: ");
                    String productName = s.nextLine();
                    System.out.println("add product's price: ");
                    int productPrice = scanner.nextInt();
                    System.out.println("add the discount percentage for a club member");
                    int discount = scanner.nextInt();
                    products.add(new Product(productName, productPrice, discount, true));
                    break;
                case 6:
                    System.out.println("choose for which product you want to change the status");
                    for (int i = 0; i < products.size(); i++) {
                        System.out.println(i + "- " + products.get(i).getProductName());
                    }
                    int productChoice = scanner.nextInt();
                    System.out.println("the status of " + products.get(productChoice).getProductName() + " is: " + products.get(productChoice).isAvailable() + "\n" +
                            "choose new status:\n 1- available\n 2- not available");
                    int statusChoice = scanner.nextInt();
                    switch (statusChoice) {
                        case AVAILABLE:
                            products.get(productChoice).setAvailable(true);
                            break;
                        case NOT_AVAILABLE:
                            products.get(productChoice).setAvailable(false);
                            break;
                        default:
                            System.out.println("invalid choice!");
                            workerMenu(currentWorker, index);
                            break;
                    }
                    break;
                case 7:
                    clientMenu(currentWorker, index);
                    break;
                case 8:
                    showWorkerMenu = false;
            }

        } while (showWorkerMenu);
        start();
    }


    public int locateIndexOfUser(String userName) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(userName)) {
                return i;
            }
        }
        return -1;
    }


    public boolean isExistingUser(String userName, String password) {
        if (!(users.isEmpty())) {
            for (Client client : users) {
                 if ((client.getUserName().equals(userName)) && (client.getPassword().equals(password))) return true; //the user was found
            }
        } else {
            System.out.println("no users in the virtualStore");

        }
        return false; //no such user
    }



    public boolean isValidName(String name) {
        boolean notValid;
        for (int i = 0; i < name.length(); i++) {
            notValid = Character.isDigit(name.charAt(i));
            if (notValid) return false;
        }
        return true;
    }

    public boolean isValidPassword (String password) {
        return password.length() >= 6;
    }

    public void addUsersToList (Client client) {
        users.add(client);
    }

    public boolean isFreeUserName(String userName) {
        if (!(users.isEmpty())) {
            for (Client client : users) {
                if (client.getUserName().equals(userName)) return false; //this user name is not free
            }
            return true; // this user name is good!
        }
        return true; // this user name is good!
    }

    public void thanksMassage() {
        System.out.println("thanks for registration!\n");

    }
}


