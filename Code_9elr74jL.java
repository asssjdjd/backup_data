import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;


class Code_9elr74jJ{

    // Hàm đảo ngược từ đầu và cuối trong name
    private static String fixName(String name) {
        String[] parts = name.split(" ");
        if (parts.length <= 1) return name; // không có gì để đảo
        StringBuilder sb = new StringBuilder();
        sb.append(parts[parts.length - 1]).append(" ");
        for (int i = 1; i < parts.length - 1; i++) {
            sb.append(parts[i]).append(" ");
        }
        sb.append(parts[0]);
        return sb.toString().trim();
    }
    
      private static int fixQuantity(int quantity) {
        String reversed = new StringBuilder(String.valueOf(quantity)).reverse().toString();
        return Integer.parseInt(reversed);
    }


    public static void main(String[] args) {
        
        // Server
        final String SERVER_HOST = "203.162.10.109";
        final int SERVER_PORT = 2209;
        final int BUFFER_SIZE = 4096;

        // Client
        final String studentCode = "B22DCCN505";
        final String qCode = "9elr74jJ";

        try(DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

            // send message
            String message = ";" + studentCode + ";" + qCode;
            System.out.println("Message is " + message);
            byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress,SERVER_PORT);
            socket.send(sendPacket);
            System.out.println("Send success");

            // receive response
            byte[] reveivedBuffer = new byte[BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(reveivedBuffer, BUFFER_SIZE);
            socket.receive(receivePacket);
            String requestId = new String(receivePacket.getData(),0,8,StandardCharsets.UTF_8);
            ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData(),8,BUFFER_SIZE);
            ObjectInputStream ois = new ObjectInputStream(bais);
            UDP.Product productData = (UDP.Product) ois.readObject();
            System.out.println("request id is " + requestId + "; Data is " + productData.toString());
            
            // Sửa lỗi tên và số lượng
            productData.setName(fixName(productData.getName()));
            productData.setQuantity(fixQuantity(productData.getQuantity()));
            System.out.println("Fixed Product: " + productData);

            // Serialize lại và gửi về server
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(productData);
            oos.flush();
            byte[] objectBytes = baos.toByteArray();

            // Ghép requestId + objectBytes
            byte[] replyData = new byte[8 + objectBytes.length];
            System.arraycopy(requestId.getBytes(StandardCharsets.UTF_8), 0, replyData, 0, 8);
            System.arraycopy(objectBytes, 0, replyData, 8, objectBytes.length);

            DatagramPacket replyPacket = new DatagramPacket(replyData, replyData.length, serverAddress, SERVER_PORT);
            socket.send(replyPacket);
            System.out.println("Reply sent!");


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

