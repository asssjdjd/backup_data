package TCP;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 20180801L;
    private int id;
    private String code;
    private String addressLine;
    private String city;
    private String postalCode;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    public Address(int id, String code, String addressLine, String city, String postalCode) {
        this.id = id;
        this.code = code;
        this.addressLine = addressLine;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Address() {
    }

    public void normalize() {

        String input = this.addressLine;
        String portal = this.postalCode;

        // 1. Loại bỏ ký tự đặc biệt, chỉ giữ chữ cái, số, khoảng trắng
        String cleaned = input.replaceAll("[^a-zA-Z0-9\\s]", "");

        // 2. Loại bỏ khoảng trắng thừa
        cleaned = cleaned.trim().replaceAll("\\s+", " ");

        // 3. Viết hoa chữ cái đầu mỗi từ
        StringBuilder result = new StringBuilder();
        for (String word : cleaned.split(" ")) {
            if (word.isEmpty()) continue;
            if (Character.isLetter(word.charAt(0))) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            } else {
                // Nếu từ bắt đầu bằng số thì giữ nguyên
                result.append(word);
            }
            result.append(" ");
        }

        String cleanedPortal = portal.replaceAll("[^0-9-]", "");

        this.postalCode = cleanedPortal;
        this.addressLine = result.toString().trim();;

    }
}
