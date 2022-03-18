import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Driver
{
    public static void createRandomDates() throws IOException
    {
        FileWriter fileWriter = new FileWriter("dates.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for(int i=0; i<100; i++)
        {
            int month = (int) (Math.random() * 12) + 1;

            int maxDays = 30;

            switch(month)
            {
                case 1: maxDays = 31; break;
                case 2: maxDays = 28; break;
                case 3: maxDays = 31; break;
                case 4: maxDays = 30; break;
                case 5: maxDays = 31; break;
                case 6: maxDays = 30; break;
                case 7: maxDays = 31; break;
                case 8: maxDays = 31; break;
                case 9: maxDays = 30; break;
                case 10: maxDays = 31; break;
                case 11: maxDays = 30; break;
                case 12: maxDays = 31; break;
            }

            int days = (int) (Math.random() * maxDays) + 1;
            int year = (int) (Math.random() * 521) + 1500;

            printWriter.write(month + " " + days + " " + year + "\n");
        }

        printWriter.close();
        fileWriter.close();
    }


    public static String getPrediction(int result)
    {
        String s = "";

        switch(result)
        {
            case 1: s= "You will have a good day"; break;
            case 2: s= "You will have a bad day"; break;
            case 3: s= "You will lose money"; break;
            case 4: s= "You will have 9 kids"; break;
            case 5: s= "You will be very poor"; break;
            case 6: s= "You will be very rich"; break;
            case 7: s= "You will walk on the moon"; break;
            case 8: s= "You will win the lottery"; break;
            case 9: s= "You will be on the news"; break;
        }

        return s;
    }

    public static int getValue(int n)
    {
        int sum = 0;

        //keep adding digits until sum becomes one single digit
        while(n > 0 || sum > 9)
        {
            //if number becomes 0, put it equal to sum and now add digits again
            if(n == 0)
            {
                n = sum;
                sum = 0;
            }

            sum += n % 10;
            n /= 10;
        }

        return sum;
    }


    // Value dates and make objects of numerology
    public static void ValueDates(SLL sll, DLL dll) throws Exception
    {
        File file = new File("dates.txt");

        Scanner sc = new Scanner(file);

        //read until the end of file
        while(sc.hasNext())
        {
            String data = sc.nextLine();

            if(data.length() < 1)
                break;

            //split the line read by space
            String splits[] = data.split(" ");


            int month = Integer.parseInt(splits[0]);
            int day = Integer.parseInt(splits[1]);
            int year = Integer.parseInt(splits[2]);

            int sum = day + month + year;
            int result = getValue(sum);
            String prediction = getPrediction(result);

            Numerology n = new Numerology(month, day, year, result, prediction);
            sll.add(n);
            dll.add(n);
        }

        sc.close();
    }


    public static void main(String args[]) throws Exception
    {
        SLL sll = new SLL();
        DLL dll = new DLL();

        createRandomDates();
        ValueDates(sll, dll);
System.out.println("Printing sll");

        SLLNode node1 = sll.getHead();

        while(node1 != null)
        {
            System.out.println(node1.numerology.toString());
            node1 = node1.next;
        }
System.out.println("Printing dll");
        DLLNode node2 = dll.getTail();

        while(node2 != null)
        {
            System.out.println(node2.numerology.toString());
            node2 = node2.prev;
        }

        FileWriter fileWriter = new FileWriter("predict.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        node1 = sll.getHead();

        while(node1 != null)
        {
            printWriter.write(node1.numerology.getPrediction() + "\n");
            node1 = node1.next;
        }

        printWriter.close();
        fileWriter.close();
    }
}