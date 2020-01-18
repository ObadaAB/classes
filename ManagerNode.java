import sun.reflect.generics.tree.Tree;

import java.beans.IntrospectionException;
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

        TreeMap<String, Integer>[] treeMaps = new TreeMap[n];

        IntStream.range(0, n).parallel().forEach(i -> {
            try
            {
                Socket socket = serverSocket.accept();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                String[] nodeWords = Arrays.copyOfRange(words, i * wordsPerNode, Math.min((i + 1) * wordsPerNode, words.length));
                objectOutputStream.writeObject(nodeWords);

                treeMaps[i] = (TreeMap<String, Integer>) objectInputStream.readObject();

                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        });

        PriorityQueue<Map.Entry<Map.Entry<String, Integer>, Integer>> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < n; i++)
        {
            priorityQueue.add(new AbstractMap.SimpleEntry<>(treeMaps[i].firstEntry(), i));
            treeMaps[i].remove(treeMaps[i].firstKey());
        }

        ArrayList<Map.Entry<String, Integer>> entryArrayList = new ArrayList<>();

        while (!priorityQueue.isEmpty())
        {
            Map.Entry<Map.Entry<String, Integer>, Integer> entry = priorityQueue.poll();
            entryArrayList.add(entry.getKey());
            int idx = entry.getValue();
            if (!treeMaps[idx].isEmpty())
            {
                priorityQueue.add(new AbstractMap.SimpleEntry<>(treeMaps[idx].firstEntry(), idx));
                treeMaps[idx].remove(treeMaps[idx].firstKey());
            }
        }

        for (Map.Entry<String, Integer> entry : entryArrayList)
        {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

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