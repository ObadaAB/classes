import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.IntStream;

public class ManagerNode
{
    private static ServerSocket serverSocket;
    private static int port = 7070;

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        String[] words = getWords();
        int n = Integer.parseInt(args[0]);
        int wordsPerNode = (words.length + n - 1) / n;

        serverSocket = new ServerSocket(port);

        IntStream.range(0, n).parallel().forEach(i -> {
            try
            {
                Socket socket = serverSocket.accept();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                String[] nodeWords = Arrays.copyOfRange(words, i * wordsPerNode, Math.min((i + 1) * wordsPerNode, words.length));
                objectOutputStream.writeObject(nodeWords);

                TreeMap<String, Integer> treeMap = (TreeMap<String, Integer>) objectInputStream.readObject();
                for (Map.Entry<String, Integer> entry : treeMap.entrySet())
                {
                    System.out.println(i + " " + entry.getKey());
                }

                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        });

        serverSocket.close();
    }

    private static String[] getWords()
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(8081);
            Socket socket = serverSocket.accept();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String[] words = (String[])objectInputStream.readObject();
            objectInputStream.close();
            socket.close();
            serverSocket.close();
            return words;
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        String [] words = {};
        return words;
    }
}