import java.math.BigInteger;
import java.util.Scanner;

public class Power
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter base and power :");
        BigInteger a = new BigInteger(in.next());
        int b = in.nextInt();

        System.out.println(a + " ^ " + b + " :");
        System.out.println(a.pow(b));
    }
}
