import java.io.BufferedReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Code_GojxxCej{
    public static void main(String[] args) {

        // Information of Server : 
        final String SERVER_HOST = "203.162.10.109";
        final int SERVER_PORT = 2208;
        final int BUFFER_SIZE = 10000;

        String studentCode = "B22DCCN505";
        String qCode = "GojxxCej";

        try(DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);
        
             // Send data :
            String message = ";" + studentCode + ";" + qCode;
            byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,serverAddress,SERVER_PORT);
            socket.send(sendPacket);
            System.out.println("Send message successful!");

            // received data : 
            byte[] receiveBuffer = new byte[BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, BUFFER_SIZE);
            socket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength(),StandardCharsets.UTF_8).trim();
            System.out.println("Data reveived : " + response);

            // handlde data and reply
            String parts[] = response.split(";");
            if(parts.length != 2) {
                throw new IllegalArgumentException("Response is not right");
            }
            String code = parts[0];
            String arrayStrings[] = parts[1].split(" ");
            
            StringBuilder sb = new StringBuilder();
            for(String string : arrayStrings) {
                String temp = string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
                sb.append(temp);
                sb.append(" ");
            }
            String data = sb.toString().trim();

            // reply
            String reply = code + ";" + data;
            byte[] replyData = reply.getBytes(StandardCharsets.UTF_8);
            DatagramPacket replyPacket = new DatagramPacket(replyData, replyData.length, serverAddress, SERVER_PORT);
            socket.send(replyPacket);
            System.out.println("Succeess reply with message " + reply);

            // close connection
            System.out.println("Close");


            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       


    }
}