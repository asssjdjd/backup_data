import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Code_4sOTm8O2 {
    private static String readLine(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int b;
        while ((b = is.read()) != -1) {
            if (b == '\n') break; // server gửi kết thúc bằng newline
            buffer.write(b);
        }
        return buffer.toString(StandardCharsets.UTF_8).trim();
    }

    public static void main(String[] args) {
        final String SERVER_HOST = "203.162.10.109";
        final int PORT = 2206;
        final int TIMEOUT = 5000;

        String message = "B22DCCN505;4sOTm8O2"; // đúng qCode

        try (Socket socket = new Socket(SERVER_HOST, PORT)) {
            socket.setSoTimeout(TIMEOUT);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // a. Gửi mã sinh viên + qCode
            os.write((message + "\n").getBytes(StandardCharsets.UTF_8));
            os.flush();
            System.out.println("Sent: " + message);

            // b. Nhận dãy số
            String response = readLine(is);
            System.out.println("Received: " + response);

            // c. Xử lý dữ liệu
            List<Integer> nums = new ArrayList<>();
            for (String s : response.split(",")) {
                nums.add(Integer.parseInt(s.trim()));
            }

            if (nums.size() < 2) {
                System.err.println("Dãy không đủ số để xử lý!");
                return;
            }

            double avg = nums.stream().mapToInt(Integer::intValue).average().orElse(0.0);
            double target = avg * 2;

            int num1 = 0, num2 = 0;
            double minDiff = Double.MAX_VALUE;

            // tìm cặp gần nhất (O(n^2), nhưng chắc mảng nhỏ nên ok)
            for (int i = 0; i < nums.size(); i++) {
                for (int j = i + 1; j < nums.size(); j++) {
                    int a = nums.get(i), b = nums.get(j);
                    double diff = Math.abs((a + b) - target);
                    if (diff < minDiff) {
                        minDiff = diff;
                        num1 = Math.min(a, b);
                        num2 = Math.max(a, b);
                    }
                }
            }

            String result = num1 + "," + num2;
            os.write((result + "\n").getBytes(StandardCharsets.UTF_8));
            os.flush();
            System.out.println("Sent result: " + result);

            // d. Đóng kết nối
            System.out.println("Done, closing socket.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
