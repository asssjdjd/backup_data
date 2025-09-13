import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Code_o1beAn0t {
    public static void main(String[] args) {

        // Information of Server
        final String SERVER_HOST =  "203.162.10.109";
        final int SERVER_PORT = 2207;
        final int BUFFER_SIZE = 4096;

        // mã sinh viên và câu hỏi
        String studentCode = "B22DCCN505";
        String qCode = "o1beAn0t";

        try(DatagramSocket socket = new DatagramSocket()) {
                InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

                // Gửi thông điệp
                String message = ";" + studentCode + ";" + qCode;
                byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,serverAddress,SERVER_PORT);
                socket.send(sendPacket);
                System.out.println("Send message success!");

                // Nhận thông điệp từ Server
                byte[] receiveBuffer = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, BUFFER_SIZE);
                socket.receive(receivePacket);
                String response = new String(receivePacket.getData(),0,receivePacket.getLength(),StandardCharsets.UTF_8).trim();
                System.out.println("Data reveived : " + response);

                // Xử lý thông điệp
                String[] parts = response.split(";");
                if(parts.length != 2) {
                    throw new IllegalArgumentException("Server Response is not right");
                }
                String requestId = parts[0];
                System.out.println("RequestId of Server : " + requestId);
                String[] numberStrings = parts[1].split(",");

                // Tim max, min
                int max = Integer.MIN_VALUE;
                int min = Integer.MAX_VALUE;
                for (String number : numberStrings) {
                    int value = Integer.parseInt(number);
                    if(value > max) max = value;
                    if(value < min)  min = value;
                }
                System.out.println("MIN and MAX : " + min + " " + max);

                // (c) Gửi lại "requestId;max,min"
                String reply = requestId + ";" + max + "," + min;
                byte[] replyData = reply.getBytes(StandardCharsets.UTF_8);
                DatagramPacket replyPacket = new DatagramPacket(replyData, replyData.length, serverAddress, SERVER_PORT);
                socket.send(replyPacket);
                System.out.println("Reply Server: " + reply);

                // Đóng soceket và kết thức chương trình 
                System.out.println("Close connection");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
