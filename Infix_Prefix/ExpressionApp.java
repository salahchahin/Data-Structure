import java.io.*;
	class ExpressionApp
{
        
   public static void main(String args[])throws IOException
   {
     String s,check="y";
     int n;
     Evaluation inf;
     DataInputStream inp=new DataInputStream(System.in);
     System.out.println("Enter the infix expression ");
     s=inp.readLine();
            inf=new Evaluation(s);
            System.out.println("Postfix expression: "+inf.inToPost());
            inf=new Evaluation(s);
            System.out.println("Prefix expression: "+inf.inToPre());

            
   }
}