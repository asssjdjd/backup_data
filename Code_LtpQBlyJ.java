import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Code_LtpQBlyJ {

    private static boolean isPrime(long s){
        if(s < 2) return false;
        for(long i = 2; i <= Math.sqrt(s); i ++) {
            if(s % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        final String SERVER_HOST = "203.162.10.109";
        final int PORT = 2206;
        final int TIMEOUT = 5000;
        int BUFFER_SIZE = 8192;

        String message = "B22DCCN505;LtpQBlyJ";

        try(Socket socket =  new Socket(SERVER_HOST, PORT)) {
            socket.setSoTimeout(TIMEOUT);

            InputStream is = (socket.getInputStream());
            OutputStream os = (socket.getOutputStream());

//            send
            os.write(message.getBytes());
            os.flush();
            System.out.println("send");
//           read

//            String res = is.toString();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String response = reader.readLine();
            System.out.println(response );
//            test

            long sum = Arrays.stream(response.split(","))
                    .mapToLong(Long::parseLong)
                    .filter(Code_LtpQBlyJ::isPrime)
                    .sum();

            System.out.println(sum);
            os.write((String.valueOf(sum)).getBytes());
            os.flush();
//            send
            System.out.println("send suceess");;

// d. Đóng kết nối
            System.out.println("Done, closing socket.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}