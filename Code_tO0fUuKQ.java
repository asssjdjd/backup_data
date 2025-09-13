import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Code_tO0fUuKQ {
    public static void main(String[] args) {
        // Server
        final String SERVER_HOST = "203.162.10.109";
        final int SERVER_PORT = 2208;

        // Client info
        final String studentCode = "B22DCCN505";
        final String qCode = "tO0fUuKQ";

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            socket.setSoTimeout(5000); // timeout 5s như đề

            // Tạo luồng đọc/ghi text
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));

            // a. Gửi message
            String message = studentCode + ";" + qCode;
            System.out.println("Message is: " + message);
            writer.write(message);
            writer.newLine(); // kết thúc dòng
            writer.flush();

            // b. Nhận danh sách domain
            String response = reader.readLine();
            System.out.println("Server says: " + response);

            // c. Lọc domain .edu
            String[] domains = response.split(",\\s*"); // tách theo dấu phẩy
            StringBuilder eduDomains = new StringBuilder();
            for (String domain : domains) {
                if (domain.trim().endsWith(".edu")) {
                    if (eduDomains.length() > 0) eduDomains.append(", ");
                    eduDomains.append(domain.trim());
                }
            }

            // Gửi lại danh sách .edu cho server
            String eduMessage = eduDomains.toString();
            System.out.println("Send back .edu domains: " + eduMessage);
            writer.write(eduMessage);
            writer.newLine();
            writer.flush();

            // d. Đóng kết nối
            System.out.println("Done, closing socket.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
