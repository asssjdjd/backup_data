import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//[Mã câu hỏi (qCode): wkOmqnh3].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209
// (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây)
// . Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectOutputStream/ObjectInputStream)
// để gửi/nhận và chuẩn hóa thông tin địa chỉ của khách hàng.
//Biết rằng lớp TCP.Address có các thuộc tính (id int, code String, addressLine String, city String, postalCode String) và
// trường dữ liệu private static final long serialVersionUID = 20180801L.
//        a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;A1B2C3D4"
//b. Nhận một đối tượng là thể hiện của lớp TCP.Address từ server. Thực hiện chuẩn hóa thông tin addressLine bằng cách:
//        •	Chuẩn hóa addressLine: Viết hoa chữ cái đầu mỗi từ, in thường các chữ còn lại, loại bỏ ký tự đặc biệt và khoảng trắng thừa (ví dụ: "123 nguyen!!! van cu" → "123 Nguyen Van Cu")
//•	Chuẩn hóa postalCode: Chỉ giữ lại số và ký tự "-" ví dụ: "123-456"
//c. Gửi đối tượng đã được chuẩn hóa thông tin
public class wkOmqnh3 {
    public static void main(String[] args) {

//       infor
        final String SERVER_HOST = "203.162.10.109";
        final int PORT = 2209;
        final int TIMEOUT = 5000;
        int BUFFER_SIZE = 8192;

        String message = "B22DCCN505;wkOmqnh3";

        try (Socket socket = new Socket(SERVER_HOST, PORT)) {
            socket.setSoTimeout(TIMEOUT);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

//            send
            oos.writeObject(message);
            oos.flush();
            System.out.println("send");

//           read
            TCP.Address product = (TCP.Address) ois.readObject();
            System.out.println(product.toString());

            product.normalize();
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
