import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TreeMap;

public class MapperNode
{
    private static Socket socket;
    private static int port = 8080;

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        String host = args[0];
        socket = new Socket(host, port);

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        String[] words = (String[])objectInputStream.readObject();

        TreeMap<String, Integer> mp = new TreeMap<>();

        for (int i = 1; i < words.length; i++)
        {
            if (mp.containsKey(words[i]))
            {
                mp.put(words[i], mp.get(words[i]) + 1);
            }
            else
            {
                mp.put(words[i], 1);
            }
        }

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(mp);

        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
    }
}
