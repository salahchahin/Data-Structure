import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
public class MyGraph
{
    private static BufferedReader graphStream;
    private static Set<Integer> nodes; 
    private static Set<String> edges;    
    static String styleSheet =
            "node{" +
                    "fill-color: red;"+
                    "size: 20px;"+
                    "text-size: 40;"+
                    "text-alignment: at-left;"+
                    "text-color: green;" +
                    "}";
    public static void main(String[] args) throws IOException {
        Graph g = null;
        String file = "C:/graph.txt"; // file path
        try
        {
            graphStream = new FileReader(file).getGraphStream(); //read and convert to graph stream
            inputGraph(); //initialize graph
            g = new  SingleGraph("my graph"); // create graph
            for (Integer node : nodes)
            {
                g.addNode(String.valueOf(node)); //extract nodes from set and add to graph one by one
            }            
            for (String edgeLine : edges) //extract edges from each line for each vertex
            {
                String edgeName="",vertexTwo="";
                String vertex = edgeLine.substring(0, edgeLine.indexOf(' ')); //first integer is vertex
                edgeLine = edgeLine.substring(edgeLine.indexOf(' ')+1); // Delete first integer from string
                while (edgeLine.length()!=0) //if it has more values
                {
                    try
                    {
                        vertexTwo = edgeLine.substring(0,edgeLine.indexOf(' ')); //extract next
                        edgeLine = edgeLine.substring(edgeLine.indexOf(' ')+1); // and delete second
                    }
                    catch (Exception e)
                    {
                        vertexTwo = edgeLine; //extract last
                        edgeLine = ""; // reset line
                    } 
                    edgeName = vertex+vertexTwo; //create edge name by concatenating indexes 
                    g.addEdge(edgeName, g.getNode(vertex), g.getNode(vertexTwo)).setAttribute("length", 1); // add edge to graph with name and vertexes
                } 
            }            
            for(Node n: g) {
                g.getNode(n.getId()).setAttribute("ui.label", n.getId()); //add node ids as labels to nodes
            }           
            g.edges().forEach( e -> e.setAttribute("distance", "" + (int) e.getNumber("length")));                      
            System.out.println("Node count: "+g.getNodeCount()+ " \nEdge Count: "+g.getEdgeCount());
            System.setProperty("org.graphstream.ui", "swing"); //swing GUI
            g.setAttribute("ui.stylesheet", styleSheet); //styling
            g.display(); //show
        }
        catch (Exception e)
        {
            e.printStackTrace();            
        }           
        calculateShortestPath(g);
    }    
    public static void calculateShortestPath(Graph g){               
        do {            
            System.out.println("Shortest distance calculator, please enter node ids:");
            Scanner scan = new Scanner(System.in);
            System.out.print("First Node ID: ");
            String firstId = scan.next();
            scan = new Scanner(System.in);
            System.out.print("Second Node ID: ");
            String secondId = scan.next();           
            // Edge lengths are stored in an attribute called "length"
            Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");
            // Compute the shortest paths in g from A to all nodes
            dijkstra.init(g);
            dijkstra.setSource(g.getNode(firstId));
            dijkstra.compute();            
            System.out.printf("Shortest distance between nodes: %.2f %n", dijkstra.getPathLength(g.getNode(secondId)));                    
            System.out.println("Do you want to calculate Again? (y/n)");
            scan = new Scanner(System.in);
            char c = scan.next().charAt(0);
            if((c == 'y')){
                for (Node node : dijkstra.getPathNodes(g.getNode(secondId)))
                    node.setAttribute("ui.style", "fill-color: black;");
            }
            else if (c == 'n'){
            	System.out.println("Exit Successfully");
                break;
            }
        }
        while (true);
    }        
    public static void inputGraph() {
        nodes = new TreeSet<>();
        edges = new TreeSet<>();
        // I'm Using sets to prevent repetition insertion       
        try {         
        String line;
        while ((line = graphStream.readLine()) != null) { //if line is read
             edges.add(line); //add line for references of vertex edges
             String integerStr = "";
             int node;
             for (int i = 0; i < line.length(); i++) //extract and save vertexes to set
             {
                char c = line.charAt(i);
                if(Character.isDigit(c)){ //if character is digit
                    integerStr +=c; //appent string
                    if(i ==line.length()-1){ //for last value
                        if(!integerStr.isEmpty()){ //if string is appended
                            try {
                                node = Integer.parseInt(integerStr); // extract integer
                                nodes.add(node); //add to set node
                            }
                            catch (Exception e)
                            {}
                        }
                        integerStr = ""; //reset string
                    }
                }
                else if(c == ' '){ //if character is space
                    if(!integerStr.isEmpty()) { // and appended string has values                        
                            node = Integer.parseInt(integerStr); //convert to integers
                            nodes.add(node); // and add to set                       
                    }
                       integerStr = ""; // reset string
                }
             }
         }
      }     
      catch (IOException io)   
      {
         System.out.println ("File has I/O problems.\n");
      }              
   }  
}
class FileReader{    
    public BufferedReader graphStream;   
    public FileReader (String filename) throws IOException {      
       graphStream = fileIn (filename); //read file      
   }   
    public BufferedReader fileIn (String file) throws FileNotFoundException {
        BufferedReader br = null;
        br = new BufferedReader (new java.io.FileReader (file));
        return br;
    }  
    public BufferedReader getGraphStream(){
        return graphStream;
    }
}