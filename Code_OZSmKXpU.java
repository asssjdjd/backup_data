import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Code_OZSmKXpU {
    public static void main(String[] args) {
        final String SERVER_HOST = "203.162.10.109";
        final int PORT = 2206;
        final int TIMEOUT = 5000;

        String message = "B22DCCN505;OZSmKXpU";

        try (Socket socket = new Socket(SERVER_HOST, PORT)) {
            socket.setSoTimeout(TIMEOUT);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // a. Gửi mã sinh viên + qCode (kèm newline để server biết kết thúc gói tin)
            os.write((message + "\n").getBytes(StandardCharsets.UTF_8));
            os.flush();
            System.out.println("Sent: " + message);

            // b. Đọc dữ liệu từ server (đọc đến khi gặp newline hoặc hết dữ liệu)
            byte[] buffer = new byte[8192];
            int len = is.read(buffer);
            if (len == -1) {
                System.err.println("No response from server.");
                return;
            }
            String response = new String(buffer, 0, len, StandardCharsets.UTF_8).trim();
            System.out.println("Received: " + response);

            // c. Xử lý chuỗi số
            int[] numbers = Arrays.stream(response.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .toArray();

            long total = Arrays.stream(numbers).asLongStream().sum();
            long left = 0;
            long bestLeft = 0, bestRight = 0;
            long minDiff = Long.MAX_VALUE;
            int bestIndex = 0;

            for (int i = 0; i < numbers.length; i++) {
                long right = total - left - numbers[i];
                long diff = Math.abs(left - right);
                if (diff < minDiff) {
                    minDiff = diff;
                    bestIndex = i;
                    bestLeft = left;
                    bestRight = right;
                }
                left += numbers[i];
            }

            // kết quả theo định dạng: "index,left,right,diff"
            String result = bestIndex + "," + bestLeft + "," + bestRight + "," + minDiff;
            os.write((result + "\n").getBytes(StandardCharsets.UTF_8));
            os.flush();
            System.out.println("Sent result: " + result);

            System.out.println("Done, closing socket.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
