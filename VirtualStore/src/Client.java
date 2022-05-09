import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


public class Client {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private float totalSumOfCart;
    private boolean clubMember;
    private boolean isWorker;


    private HashSet<Product> cart = new HashSet<>();


    public Client(String firstName, String lastName, String userName, String password, boolean clubMember, boolean isWorker) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.clubMember = clubMember;
        this.isWorker = isWorker;

    }

    public Client() {
    }



    public void addToCart (Product userProduct) {
        cart.add(userProduct);
    }


    public int getTotalSumOfCart () {
        if (!(cart.isEmpty())) {
            int totalSum = 0;
            if (this.isClubMember()) {
                for (Product product : cart) {
                    totalSum += (product.getPriceAfterDiscount() * product.getAmount());
                }
            } else {
                for (Product product : cart) {
                    totalSum += (product.getPrice() * product.getAmount());
                }
            }
            return totalSum;
        } else
            System.out.println("No products in cart");
            return 0;
    }

    public void setTotalSumOfCart (float totalSumOfCart) {
        this.totalSumOfCart = totalSumOfCart;
    }


    public HashSet<Product> getCart() {
        return cart;
    }


    public boolean isWorker() {
        return isWorker;
    }

    public void setWorker(boolean worker) {
        isWorker = worker;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isClubMember() {
        return clubMember;
    }

    public void setClubMember(boolean clubMember) {
        this.clubMember = clubMember;
    }
}
