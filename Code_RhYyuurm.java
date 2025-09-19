import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

//[Mã câu hỏi (qCode): RhYyuurm].
// Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây).
// Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
//
//a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;C1234567"
//
//b. Nhận từ server một chuỗi chứa nhiều từ, các từ được phân tách bởi khoảng trắng. Ví dụ: "hello world this is a test example"
//
//c. Sắp xếp các từ trong chuỗi theo độ dài, thứ tự xuất hiện. Gửi danh sách các từ theo từng nhóm về server theo định dạng: "a, is, this, test, hello, world, example".
//
//d. Đóng kết nối và kết thúc chương trình.
public class Code_RhYyuurm {

    public static void main(String[] args) {

//      socket parameter
        final String SERVER_HOST = "203.162.10.109";
        final int PORT = 2208;
        final int TIMEOUT = 5000;
        int BUFFER_SIZE = 8192;
        String message = "B22DCCN505;RhYyuurm";

        try(Socket socket =  new Socket(SERVER_HOST, PORT)) {

            socket.setSoTimeout(TIMEOUT);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

//            send message
            writer.write(message);
            writer.newLine();
            writer.flush();
            System.out.println("send success !");

//            received data
            String response = reader.readLine();
            System.out.println(response);

//            handle request
            List<String> strs = Arrays.asList(response.split(" "));
            strs.sort(Comparator.comparingInt(String::length));
            String result = String.join(", ",strs);
            System.out.println(result);

//            send
            writer.write(result);
            writer.newLine();
            writer.flush();

// d. Đóng kết nối
            System.out.println("Done, closing socket.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
