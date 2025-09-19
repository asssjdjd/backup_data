//[Mã câu hỏi (qCode): XfLAua8y].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208
// (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây).
// Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
//a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode".
//Ví dụ: "B15DCCN999;1D08FX21"
//b. Nhận từ server một chuỗi chứa nhiều từ, các từ được phân tách bởi khoảng trắng.
//Ví dụ: "hello world programming is fun"
//c. Thực hiện đảo ngược từ và mã hóa RLE để nén chuỗi ("aabb" nén thành "a2b2"). Gửi chuỗi đã được xử lý lên server. Ví dụ: "ol2eh dlrow gnim2argorp si nuf".
//d. Đóng kết nối và kết thúc chương trình

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class XfLAua8y {

    public static String reverseWords(String input) {
        String[] words = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            sb.append(new StringBuilder(words[i]).reverse());
            if (i < words.length - 1) sb.append(" ");
        }
        return sb.toString();
    }

    public static String encode(String input) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i <= input.length(); i++) {
            if (i < input.length() && input.charAt(i) == input.charAt(i - 1)) {
                count++;
            } else {
                sb.append(input.charAt(i - 1));
                if(count != 1) sb.append(count);
                count = 1;
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        //      socket parameter
        final String SERVER_HOST = "203.162.10.109";
        final int PORT = 2208;
        final int TIMEOUT = 5000;
        int BUFFER_SIZE = 8192;
        String message = "B22DCCN505;XfLAua8y";

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
            String reverseResponse = reverseWords(response);
            String[] strs = reverseResponse.split(" ");

            String result = "";
            for(int i = 0; i < strs.length; i++) {
                result += encode(strs[i]) + " ";
            }
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
