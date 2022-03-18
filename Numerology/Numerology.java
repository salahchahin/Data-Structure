import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class Numerology
{
    private int month, day, year, result;
    private String prediction;

    public Numerology(int m, int d, int y, int r, String p)
    {
	day = d;
        month = m;
        year =y;
	result = r;
        prediction = p;
    }

    public Numerology()
    {
        month = day = year = result = 0;
        prediction = null;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public String toString()
    {
        return month + "/" + day + "/" + year +" : " + prediction;
    }
}

class SLLNode
{
    public Numerology numerology;
    public SLLNode next;

    public SLLNode(Numerology n)
    {
        numerology = n;
        next = null;
    }
}


class DLLNode
{
    public Numerology numerology;
    public DLLNode next, prev;

    public DLLNode(Numerology n)
    {
        numerology = n;
        next = null;
        prev = null;
    }
}


class SLL
{
    private SLLNode head = null, tail = null;

    public SLL() {}

    public SLLNode getHead() { return head; }

    public void add(Numerology n)
    {
        SLLNode node = new SLLNode(n);

        if(head == null)
        {
            head = tail = node;
        }
        else
        {
            tail.next = node;
            tail = node;
        }
    }
}

class DLL
{
    private DLLNode head = null, tail = null;

    public DLL() {}

    public DLLNode getHead() { return head; }
    public DLLNode getTail() { return tail; }

    public void add(Numerology n)
    {
        DLLNode node = new DLLNode(n);

        if(head == null)
        {
            head = tail = node;
        }
        else
        {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
    }
}


