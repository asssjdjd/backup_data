import UDP.Book;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Code_HawKOD9F {
    public static void main(String[] args) {
        final String SERVER_HOST = "203.162.10.109";
        final int SERVER_PORT = 2209;
        final String studentCode = "B22DCCN505";
        final String qCode = "HawKOD9F";
        final int BUFFER_SIZE = 8192;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

            // send request
            String message = ";" + studentCode + ";" + qCode;
            byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            socket.send(sendPacket);
            System.out.println("Sent: " + message);

            // receive
            byte[] recvBuf = new byte[BUFFER_SIZE];
            DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
            socket.receive(recvPacket);
            int len = recvPacket.getLength();
            System.out.println("Received packet length = " + len);

            if (len < 9) {
                System.err.println("Packet too short to contain requestId + object");
                return;
            }

            // keep raw 8 bytes of requestId (do NOT re-encode from String)
            byte[] requestIdBytes = Arrays.copyOfRange(recvPacket.getData(), 0, 8);
            String requestIdStr = new String(requestIdBytes, StandardCharsets.UTF_8);
            System.out.println("RequestID (raw): " + Arrays.toString(requestIdBytes));
            System.out.println("RequestID (str) : '" + requestIdStr + "'");

            int objectLen = len - 8;
            ByteArrayInputStream bais = new ByteArrayInputStream(recvPacket.getData(), 8, objectLen);
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object obj = ois.readObject();
                if (!(obj instanceof Book)) {
                    System.err.println("Received object not instance of UDP.Book, class=" + obj.getClass().getName());
                    return;
                }
                Book book = (Book) obj;
                System.out.println("Before normalize: " + book);

                // normalize and send back
                book.normalize();
                System.out.println("After normalize: " + book);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(book);
                    oos.flush();
                }
                byte[] bookData = baos.toByteArray();

                byte[] replyData = new byte[8 + bookData.length];
                // copy original raw 8 bytes requestId
                System.arraycopy(requestIdBytes, 0, replyData, 0, 8);
                // copy serialized book after offset 8
                System.arraycopy(bookData, 0, replyData, 8, bookData.length);

                DatagramPacket replyPacket = new DatagramPacket(replyData, replyData.length, serverAddress, SERVER_PORT);
                socket.send(replyPacket);
                System.out.println("Reply sent, bytes=" + replyPacket.getLength());
            }

        } catch (Exception e) {
            System.err.println("Error in client:");
            e.printStackTrace();
        }
    }
}

