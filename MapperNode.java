import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TreeMap;

public class MapperNode
{
    private static Socket socket;
    private static int port = 8082;

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        String host = args[0];
        socket = new Socket(host, port);

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        String[] words = (String[])objectInputStream.readObject();

        for (String word : words)
        {
            System.out.println(word);
        }

        objectInputStream.close();
        socket.close();
    }
}
