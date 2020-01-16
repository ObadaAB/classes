import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Worker
{
    private static Socket socket;
    private static int port = 8080;

    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        String host = scanner.next();
        port = scanner.nextInt();
        socket = new Socket(host, port);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("IT'S WORKING!!!!");

        dataOutputStream.close();
        socket.close();
    }
}
