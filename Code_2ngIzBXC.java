//[Mã câu hỏi (qCode): 2ngIzBXC].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectOutputStream/ObjectInputStream) theo kịch bản dưới đây:
//
//Biết lớp TCP.Product gồm các thuộc tính (id int, name String, price double, int discount) và private static final long serialVersionUID = 20231107;
//
//a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1E08CA31"
//
//b. Nhận một đối tượng là thể hiện của lớp TCP.Product từ server.
//
//c. Tính toán giá trị giảm giá theo price theo nguyên tắc: Giá trị giảm giá (discount) bằng tổng các chữ số trong phần nguyên của giá sản phẩm (price). Thực hiện gán giá trị cho thuộc tính discount và gửi lên đối tượng nhận được lên server.
//
//d. Đóng kết nối và kết thúc chương trình.

import java.io.*;
import java.net.Socket;

public class Code_2ngIzBXC {
    public static void main(String[] args) {

//       infor
        final String SERVER_HOST = "203.162.10.109";
        final int PORT = 2209;
        final int TIMEOUT = 5000;
        int BUFFER_SIZE = 8192;

        String message = "B22DCCN505;2ngIzBXC";

        try(Socket socket =  new Socket(SERVER_HOST, PORT)) {
            socket.setSoTimeout(TIMEOUT);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

//            send
            oos.writeObject(message);
            oos.flush();
            System.out.println("send");

//           read
            TCP.Product product = (TCP.Product) ois.readObject();
//            test
            System.out.println(product.toString());
            int price = (int) product.getPrice();
            int discount = product.sumDigits(price);
            product.setDiscount(discount);

            System.out.println(product.toString());
//            send
            oos.writeObject(product);
            System.out.println("send suceess");;




// d. Đóng kết nối
            System.out.println("Done, closing socket.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

