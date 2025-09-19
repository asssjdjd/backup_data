//[Mã câu hỏi (qCode): 3hPA07vB].  Một chương trình máy chủ cho phép kết nối qua TCP tại cổng 2207
// (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5s), yêu cầu xây dựng chương trình (tạm gọi là client)
// thực hiện kết nối tới server tại cổng 2207, sử dụng luồng byte dữ liệu (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
//a.	Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1D25ED92"
//b.	Nhận lần lượt hai số nguyên a và b từ server
//c.	Thực hiện tính toán tổng, tích và gửi lần lượt từng giá trị theo đúng thứ tự trên lên server
//d.	Đóng kết nối và kết thúc

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;

public class Code_3hPA07vB {

    public static void main(String[] args) {

        final String SERVER_HOST = "203.162.10.109";
        final int PORT = 2207;
        final int TIMEOUT = 5000;
        int BUFFER_SIZE = 8192;

        String message = "B22DCCN505;3hPA07vB";

        try(Socket socket =  new Socket(SERVER_HOST, PORT)) {
            socket.setSoTimeout(TIMEOUT);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());


//            send message
            dos.writeUTF(message);
            dos.flush();
            System.out.println("send success !");

//            received data

//            type String
//            byte[] data = is.readAllBytes();
//            String response = new String(data, 0, data.length).trim();
//            System.out.println("Response is " + response);

            long a = dis.readInt();
            long b = dis.readInt();

            long sum = a + b;
            long mul = a * b;

            System.out.println(a + " " + b);

//            send
            dos.writeInt((int) sum);
            dos.flush();
            dos.writeInt((int) mul);
            dos.flush();

// d. Đóng kết nối
            System.out.println("Done, closing socket.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
