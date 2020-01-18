import sun.reflect.generics.tree.Tree;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TreeMap;

public class MapperNode
{
    private static Socket socket;
    private static int port = 7070;

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        String host = args[0];
        socket = new Socket(host, port);

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        String[] words = (String[])objectInputStream.readObject();

        TreeMap<String, Integer> treeMap = new TreeMap<>();
        for (String word : words)
        {
            if (treeMap.containsKey(word))
            {
                treeMap.put(word, treeMap.get(word) + 1);
            }
            else
            {
                treeMap.put(word, 1);
            }
        }

        objectOutputStream.writeObject(treeMap);

        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
    }
}
