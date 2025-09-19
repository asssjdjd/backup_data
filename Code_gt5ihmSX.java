//[Mã câu hỏi (qCode): gt5ihmSX].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207
// (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây).
// Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data
// (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B10DCCN003;C6D7E8F9"
//b. Nhận lần lượt:
//        •	Một số nguyên k là độ dài đoạn.
//        •	Chuỗi chứa mảng số nguyên, các phần tử được phân tách bởi dấu phẩy ",".
//Ví dụ: Nhận k = 3 và "1,2,3,4,5,6,7,8".
//c. Thực hiện chia mảng thành các đoạn có độ dài k và đảo ngược mỗi đoạn, sau đó gửi mảng đã xử lý lên server. Ví dụ: Với k = 3 và mảng "1,2,3,4,5,6,7,8", kết quả là "3,2,1,6,5,4,8,7". Gửi chuỗi kết quả "3,2,1,6,5,4,8,7" lên server.
//d. Đóng kết nối và kết thúc chương trình

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Code_gt5ihmSX {
    public static void main(String[] args) {

        final String SERVER_HOST = "203.162.10.109";
        final int SERVBR_PORT = 2207;
        final int BUFFER_SIZE = 4096;

        final String message = "B22DCCN505;gt5ihmSX";

        try(Socket socket = new Socket(SERVER_HOST,SERVBR_PORT)) {

//            input,output
            DataInputStream  dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());


//            send message
            dos.writeUTF(message);
            dos.flush();
            System.out.println("send success !");

//            received message
            int k =  dis.readInt();

            String response = dis.readUTF();
            System.out.println("Response is : " + " " + response);

//          handle request
            List<String> numbers = Arrays.asList(response.split(","));
            int len = numbers.toArray().length;
            System.out.println(len + " " + k);

            Stack<String> stack = new Stack<>();
            int index = 0;
            String result = "";

            for(int i = 0; i < len; i++) {
                if(index == k && !stack.empty()) {
                    while(!stack.empty()) {
                        result += stack.pop() + ",";
                    }
                    index = 0;
                }
                stack.push(numbers.get(i));
                index++;
            }
            if(!stack.empty()) {
                while(!stack.empty()) {
                    result += stack.pop() + ",";
                }
            }
            System.out.println(result.substring(0,result.length()-1));

//            reply to server
            dos.writeUTF(result.substring(0,result.length()-1));
            dos.flush();
            System.out.println("reply suceess!");

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
