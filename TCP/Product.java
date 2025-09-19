package TCP;

import java.io.Serializable;
//(id int, name String, price double, int discount)

public class Product implements Serializable {
    private static final long serialVersionUID = 20231107L;
    private int id;
    private String name;
    private double price;
    private int discount;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Product(int id, String name, double price, int discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }

    public int sumDigits(int number) {
        int sum = 0;

        // Ensure the number is positive for the calculation
        int tempNumber = Math.abs(number);

        while (tempNumber != 0) {
            sum += tempNumber % 10;
            tempNumber /= 10;
        }

        return sum;
    }

}
