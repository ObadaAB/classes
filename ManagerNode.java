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

        PriorityQueue<MyTuple> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < n; i++)
        {
            priorityQueue.add(new MyTuple(treeMaps[i].firstKey(), treeMaps[i].get(treeMaps[i].firstKey()), i));
            treeMaps[i].remove(treeMaps[i].firstKey());
        }

        ArrayList<MyTuple> entryArrayList = new ArrayList<>();

        while (!priorityQueue.isEmpty())
        {
            MyTuple tuple = priorityQueue.poll();
            entryArrayList.add(tuple);
            int idx = tuple.getIndex();
            if (!treeMaps[idx].isEmpty())
            {
                priorityQueue.add(new MyTuple(treeMaps[idx].firstKey(), treeMaps[idx].get(treeMaps[idx].firstKey()), idx));
                treeMaps[idx].remove(treeMaps[idx].firstKey());
            }
        }

        for (MyTuple tuple : entryArrayList)
        {
            System.out.println(tuple.getKey() + " " + tuple.getValue());
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

class MyTuple implements Comparable<MyTuple>
{
    private String key;
    private int value;
    private int index;

    public MyTuple(String key, int values, int index)
    {
        this.key = key;
        this.value = values;
        this.index = index;
    }

    @Override
    public int compareTo(MyTuple myTuple)
    {
        return key.compareTo(myTuple.key);
    }

    public String getKey()
    {
        return key;
    }

    public int getValue()
    {
        return value;
    }

    public int getIndex()
    {
        return index;
    }
}