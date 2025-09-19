import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class Code_Lo6eYd6F {
    public static void main(String[] args) {
        final String SERVER_HOST = "203.162.10.109";
        final int SERVER_PORT = 2207;
        final int TIMEOUT = 5000;

        final String message = "B22DCCN505;Lo6eYd6F"; // đổi sang mã SV của bạn

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            socket.setSoTimeout(TIMEOUT);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // a. Gửi mã sinh viên;qCode
            dos.writeUTF(message);
            dos.flush();
            System.out.println("Sent: " + message);

            // b. Nhận chuỗi số từ server
            String response = dis.readUTF();
            System.out.println("Received: " + response);

            int[] arr = Arrays.stream(response.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .toArray();

            // c. Tính số lần đổi chiều + tổng độ biến thiên
            int turns = 0;
            int variation = 0;

            for (int i = 1; i < arr.length; i++) {
                variation += Math.abs(arr[i] - arr[i - 1]);
            }

            // kiểm tra đổi chiều
            int prevDir = 0; // 0: chưa xác định, 1: tăng, -1: giảm
            for (int i = 1; i < arr.length; i++) {
                int diff = arr[i] - arr[i - 1];
                int dir = diff > 0 ? 1 : (diff < 0 ? -1 : 0);

                if (dir != 0) {
                    if (prevDir != 0 && dir != prevDir) {
                        turns++;
                    }
                    prevDir = dir;
                }
            }

            System.out.println("Turns: " + turns);
            System.out.println("Variation: " + variation);

            // gửi kết quả về server
            dos.writeInt(turns);
            dos.writeInt(variation);
            dos.flush();
            System.out.println("Sent results to server.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
