import java.io.DataInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class Manager
{
    private static ServerSocket serverSocket;
    private static int port = 8080;

    public static void main(String[] args) throws IOException
    {
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String s = dataInputStream.readUTF();
        System.out.println(s);

        dataInputStream.close();
        socket.close();
        serverSocket.close();
    }
}
