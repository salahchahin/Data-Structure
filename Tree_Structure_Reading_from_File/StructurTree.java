import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class assign3 {

	public static Node ROOT;
	public static int NODE_COUNT = 0;
	public static int B = 2;
	public static int VISITED = 0;
	public static int CURRENT_VISITED = 0;

	static class Node {
		int data;
		ArrayList<Node> child = new ArrayList<>();

		Node() {
		}

	}

	public static void preOrderTraversal(Node parentNode) {
		System.out.print(parentNode.data + " ");
		for (Node node : parentNode.child) {
			preOrderTraversal(node);
		}
	}

	public static void addNode(int data, int parentData) {
		if (ROOT == null) {
			ROOT = new Node();
			ROOT.data = parentData;
			NODE_COUNT++;
			addNode(data, parentData);
		} else {
			Node newNode = new Node();
			newNode.data = data;
			if (parentData == ROOT.data) {
				ROOT.child.add(newNode);
			} else {
				getParentNode(parentData, ROOT).child.add(newNode);
			}
		}
		NODE_COUNT++;
	}

	public static Node getParentNode(int parentData, Node rootNode) {
		for (Node node : rootNode.child) {
			if (node.data == parentData) {
				return node;
			} else {
				Node newNode = getParentNode(parentData, node);
				if (newNode != null) {
					return newNode;
				}
			}
		}
		return null;
	}

	public static boolean printParentData(Node node, Node root){
		for (Node each : root.child){
			if (node.data==each.data){
				System.out.print(root.data+"-");
				return true;
			}
			else{
				boolean flag = printParentData(node,each);
				if (flag){
					System.out.print(root.data+"-");
					return true;
				}
			}
		}
		return false;
	}

	public static void visitNodes(Node parentNode){

		for (Node node : parentNode.child) {
			if(parentNode.data!=ROOT.data) {
				System.out.print(parentNode.data + "-");
			}
			VISITED++;
			CURRENT_VISITED++;
			if (CURRENT_VISITED >= (B / 2)) {
				System.out.print(node.data + "-");
				printParentData(node,ROOT);
			} else {
				if (node.child.size() == 0) {
					VISITED++;
					System.out.print(node.data + "-");
					printParentData(node,ROOT);

				} else {
					visitNodes(node);
				}
			}
			CURRENT_VISITED--;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("tree.txt");
		if (!file.exists()) {
			System.out.println("No File Specified");
			System.exit(0);
		}

		Scanner scanner = new Scanner(file);


		System.out.println("Stored Tree Structure: ");

		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();
			String[] token = line.split(" ");
			int parentData = Integer.parseInt(token[0]);

			System.out.print(parentData + " ");

			int i = 1;
			while (i < token.length) {
				int data = Integer.parseInt(token[i]);
				addNode(data, parentData);

				System.out.print(data + " ");
				i++;
			}
			System.out.println();
		}
		System.out.print("Pre-order Traver: ");
		preOrderTraversal(ROOT);


		System.out.print("\nEnter Depth B: ");
		Scanner sc = new Scanner(System.in);
		B = sc.nextInt();

		System.out.print("Path: 1-");
		VISITED++;
		visitNodes(ROOT);
		System.out.println("\nPercentage of exploration: "+(VISITED*100)/NODE_COUNT+"%");
	}
}
