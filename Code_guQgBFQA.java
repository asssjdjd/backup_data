import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

class Code_guQgBFQA {

    public static void main(String[] args) {
        // Server
        final String SERVER_HOST = "203.162.10.109";
        final int SERVER_PORT = 2209;
        final int BUFFER_SIZE = 4096;

        // Client
        final String studentCode = "B22DCCN505";
        final String qCode = "guQgBFQA";

        try (DatagramSocket socket = new DatagramSocket()) {

            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

            // 1. Gửi message
            String message = ";" + studentCode + ";" + qCode;
            System.out.println("Message is " + message);
            byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            socket.send(sendPacket);
            System.out.println("Send message success!");

            // 2. Nhận response
            byte[] receiveBuffer = new byte[BUFFER_SIZE];
            DatagramPacket receiveData = new DatagramPacket(receiveBuffer, BUFFER_SIZE);
            socket.receive(receiveData);

            String requestId = new String(receiveData.getData(), 0, 8, StandardCharsets.UTF_8);

            ByteArrayInputStream bais = new ByteArrayInputStream(receiveData.getData(), 8, receiveData.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            UDP.Student studentData = (UDP.Student) ois.readObject();
            System.out.println("RequestId: " + requestId + " | Student before process: " + studentData.toString());

            // 3. Chuẩn hóa tên
            String normalizedName = normalizeName(studentData.getName());
            studentData.setName(normalizedName);

            // 4. Tạo email
            String email = buildEmail(normalizedName);
            studentData.setEmail(email);

            System.out.println("Student after process: " + studentData.toString());

            // 5. Serialize + gửi lại
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(studentData);
            oos.flush();
            byte[] objectBytes = baos.toByteArray();

            byte[] replyData = new byte[8 + objectBytes.length];
            System.arraycopy(requestId.getBytes(StandardCharsets.UTF_8), 0, replyData, 0, 8);
            System.arraycopy(objectBytes, 0, replyData, 8, objectBytes.length);

            DatagramPacket replyPacket = new DatagramPacket(replyData, replyData.length, serverAddress, SERVER_PORT);
            socket.send(replyPacket);
            System.out.println("Reply sent!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm chuẩn hóa tên
    private static String normalizeName(String name) {
        String[] parts = name.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            sb.append(Character.toUpperCase(p.charAt(0)))
              .append(p.substring(1))
              .append(" ");
        }
        return sb.toString().trim();
    }

    // Hàm tạo email theo quy tắc đề bài
    private static String buildEmail(String name) {
        String[] parts = name.toLowerCase().split("\\s+");
        String lastName = parts[parts.length - 1]; // tên
        StringBuilder sb = new StringBuilder(lastName);
        for (int i = 0; i < parts.length - 1; i++) {
            sb.append(parts[i].charAt(0)); // chữ cái đầu họ + tên đệm
        }
        sb.append("@ptit.edu.vn");
        return sb.toString();
    }
}

