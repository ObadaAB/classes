import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TreeMap;

public class Mapper
{
    private static Socket socket;
    private static int port = 8080;

    public static void main(String[] args) throws IOException
    {
        String host = args[0];
        socket = new Socket(host, port);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        TreeMap<String, Integer> mp = new TreeMap<>();

        for (int i = 1; i < args.length; i++)
        {
            if (mp.containsKey(args[i]))
            {
                mp.put(args[i], mp.get(args[i]) + 1);
            }
            else
            {
                mp.put(args[i], 1);
            }
        }

        objectOutputStream.writeObject(mp);

        objectOutputStream.close();
        socket.close();
    }
}
