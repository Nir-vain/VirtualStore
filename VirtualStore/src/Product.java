public class Product {

    private String productName;
    private int price;
    private int discountPercentage;
    private int amount = 0;
    private float priceAfterDiscount;
    private boolean isAvailable;


    public Product(String productName, int price, int discountPercentage, boolean isAvailable) {
        this.productName = productName;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.isAvailable = isAvailable;
    }

    public Product() {
    }

    public int getAmount() {
        return amount;
    }

    public void initAmount () {
        this.amount = 0;
    }

    public void increaseAmount() { this.amount += amount;}

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public float getPriceAfterDiscount() {
        return (price - (((float)price * (float)discountPercentage) / 100));
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
