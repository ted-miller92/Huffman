import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Testing {
	public static void main(String[] args) throws FileNotFoundException {
		
		// make tree from .code file
		Scanner codeInput = new Scanner(new File("short.code"));
		HuffmanTree t = new HuffmanTree(codeInput);
		
		// print tree
		inOrder(t.root);
	}
	public static void inOrder(HuffmanNode node) {
	    if (node == null) {
	      return;
	    }
	    if (node.left == null && node.right == null) {
	    	System.out.print(node.ascii + " ");
	    }
	    inOrder(node.left);
	    inOrder(node.right);
	}
}
