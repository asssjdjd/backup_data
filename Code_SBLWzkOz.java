import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Code_SBLWzkOz {
     public static void main(String[] args) {

        // Server
        final String SERVER_HOST = "203.162.10.109";
        final int SERVER_PORT = 2207;

        // Client
        final String studentCode = "B22DCCN505";
        final String qCode = "SBLWzkOz";

        // BUFFER 
        final int BUFFER_SIZE = 8192;

        // Socket
        try(DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

            // Send
            String message = ";" + studentCode + ";" + qCode;
            byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,serverAddress,SERVER_PORT);
            socket.send(sendPacket);
            System.out.println("Send message : " + message + " successful!");

            // Received
            byte[] receivedData = new byte[BUFFER_SIZE];
            DatagramPacket receivedPacket = new DatagramPacket(receivedData, BUFFER_SIZE);
            socket.receive(receivedPacket);
            String response = new String(receivedPacket.getData(),0,receivedPacket.getLength(),StandardCharsets.UTF_8);
            System.out.println("Reponse from Sever is : " + response);

            // handle required
            String[] parts = response.split(";");
            if(parts.length != 2) {
                throw new IllegalArgumentException("Response is'nt right");
            }
            String requestId = parts[0];
            String[] arrayNumbers = parts[1].split(",");
            int max_value = Integer.MIN_VALUE;
            int min_value = Integer.MAX_VALUE;
            int max_second = Integer.MIN_VALUE;
            int  min_second = Integer.MAX_VALUE;

            // find max and min
            for(String temp : arrayNumbers) {
                int value = Integer.parseInt(temp);
                max_value = Math.max(value, max_value);    
                min_value = Math.min(min_value,value);   
            }
            // find second_max and second_min
            for(String temp : arrayNumbers) {
                int value = Integer.parseInt(temp);
                if(value != max_value && value != min_value) {
                    max_second = Math.max(value, max_second);    
                    min_second = Math.min(min_second,value); 
                }
            }
            // reply
            String replyMessage = requestId + ";" + max_second + "," + min_second;
            byte[] replyData = replyMessage.getBytes(StandardCharsets.UTF_8);
            DatagramPacket replyPacket = new DatagramPacket(replyData,replyData.length,serverAddress,SERVER_PORT);
            socket.send(replyPacket);
            
            System.out.println("Reply message : " + replyMessage + " successful!");
            System.out.println("Close progame");

        } catch (Exception ex) {
            System.out.println("Something was wrong");
            ex.getStackTrace();
        }
    }
}
