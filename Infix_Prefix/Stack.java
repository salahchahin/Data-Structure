import java.io.*;
class Stack
{
   private char[] a;
   private int top,m;
   public Stack(int max)
   {
     m=max;
     a=new char[m];
     top=-1;
   }
   public void push(char key)
   {
     a[++top]=key;
   }
   public char pop()
   {
     return(a[top--]);
   }
   public char peek()
   {
     return(a[top]);
   }
   public boolean isEmpty()
   {
     return (top==-1);
   }
}
class Evaluation
{
   private Stack s;
   private String input;
   private String output="";
   public Evaluation(String str)
   {
     input=str;
     s=new Stack(str.length());
   }
   public String inToPre()
   {
     for(int i=input.length()-1;i>=0;i--)
     {
       char ch=input.charAt(i);
       switch(ch)
       {
         case '+':
         case '-':gotOperator(ch,1,')');
            break;
         case '*':
         case '/':gotOperator(ch,2,')');
            break;
         case ')':s.push(ch);
              break;
         case '(':gotParenthesis(')');
            break;
         default:output=ch+output;
       }
     }
     while(!s.isEmpty())
       output=s.pop()+output;
     return output;
   }
   public String inToPost()
   {
     for(int i=0;i<input.length();i++)
     {
       char ch=input.charAt(i);
       switch(ch)
       {
         case '+':
         case '-':gotOperator(ch,1,'(');
            break;
         case '*':
         case '/':gotOperator(ch,2,'(');
            break;
         case '(':s.push(ch);
              break;
         case ')':gotParenthesis('(');
            break;
         default:output=output+ch;
       }
     }
     while(!s.isEmpty())
       output=output+s.pop();
     return output;
   }
   public String preToPost()
   {
     Stack f=new Stack(input.length());
     for(int i=0;i<input.length();i++)
     {
       char ch=input.charAt(i);
       if(ch=='+'||ch=='-'||ch=='*'||ch=='/')
       {
         s.push(ch);
         f.push('0');
       }
       else
       {
         output=output+ch;
         while(f.peek()=='1')
         {
           output=output+s.pop();
           f.pop();
           if(f.isEmpty())
             break;
         }
         if(!f.isEmpty())
           f.pop();
         f.push('1');
       }
     }
     return output;
   }
   private void gotOperator(char opThis,int prec1,char x)
   {
     while(!s.isEmpty())
     {
       char opTop=s.pop();
       if(opTop==x)
       {
         s.push(opTop);
         break;
       }
       else
       {
         int prec2;
         if(opTop=='+'||opTop=='-')
           prec2=1;
         else
           prec2=2;
         if(prec2<prec1&&x=='(')
         {
           s.push(opTop);
           break;
         }
         else if(prec2<=prec1&&x==')')
         {
           s.push(opTop);
           break;
         }
         else
         {
           if(x==')')
             output=opTop+output;
           else
             output=output+opTop;
         }
       }
     }
     s.push(opThis);
   }
   private void gotParenthesis(char x)
   {
     while(!s.isEmpty())
     {
       char ch=s.pop();
       if(ch==x)
         break;
       else
       {
         if(x==')')
            output=ch+output;
         else
            output=output+ch;
       }
     }
   }
}