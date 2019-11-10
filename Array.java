import java.util.Scanner;

public class Array
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the number of elements :");
        int n = 0;
        try
        {
            n = in.nextInt();
        }
        catch (Exception e)
        {
            return;
        }

        int [] a = new int[n];

        System.out.println("Enter " + n + " integers :");

        for (int i = 0; i < n; i++)
        {
            a[i] = in.nextInt();
        }

        int mx = a[0], mn = a[0];
        double avg = 0;
        for (int i = 0; i < n; i++)
        {
            mx = Math.max(mx, a[i]);
            mn = Math.min(mn, a[i]);
            avg = avg + ((double)a[i]) / n;
        }

        System.out.println("Maximum : " + mx);
        System.out.println("Minimum : " + mn);
        System.out.println("Average : " + avg);
    }
}
