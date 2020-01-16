import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class Manager
{
    private static ServerSocket serverSocket;
    private static int port = 8080;

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        serverSocket = new ServerSocket(port);

        int n = Integer.parseInt(args[0]);

        for (int i = 0; i < n; i++)
        {
            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            TreeMap<String, Integer> treeMap = (TreeMap<String, Integer>) objectInputStream.readObject();
            for (Map.Entry<String, Integer> x : treeMap.entrySet())
            {
                System.out.println(i + " " + x.getKey() + " " + x.getValue());
            }
            objectInputStream.close();
            socket.close();
        }

        serverSocket.close();
    }
}