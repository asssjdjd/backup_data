import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

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

public class Test {
    public static boolean isPrime(long n) {
        for (long i = 2; i <= Math.sqrt(n); i++) {
            if (n%i == 0) return false;
        }
        return n > 1;
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("203.162.10.109", 2206);
            socket.setSoTimeout(5000);

            // a
            OutputStream os = socket.getOutputStream();
            os.write("B22DCCN505;LtpQBlyJ".getBytes());

            // b
            InputStream is = socket.getInputStream();
            byte[] buff = new byte[1024];
            int len = is.read(buff);
            String data = new String(buff, 0, len).trim();

            // c
            long sum = Arrays.stream(data.split(","))
                    .map(String::trim)
                    .mapToLong(Long::parseLong)
                    .filter(e -> isPrime(e))
                    .sum();
            os.write((sum + "").getBytes());

            // d
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
