import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanTree {
	public HuffmanNode root;
	
	/*
	 * Constructor given an array
	 * Index of array represents the ascii value
	 * Value at index represents frequency of that ascii in the file
	 */
	public HuffmanTree(int[] count) {	
		// create a priority queue
		PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>();
		
		// add all nodes to PriorityQueue
		for (int i = 0; i < count.length; i++) {
			if (i != 0 && count[i] > 0) {
				queue.offer(new HuffmanNode(i, count[i]));
			}
		}
		
		// designated eof node
		queue.offer(new HuffmanNode(256, 1));
		
		// start combining nodes of PriorityQueue into a tree
		while (queue.size() > 1) {
			// the head of the queue being the smallest frequency
			HuffmanNode tempLeft = queue.poll();
			// the new head of the queue being the next smallest frequency
			HuffmanNode tempRight = queue.poll();
			
			// a new parent node with the two previous nodes as children
			HuffmanNode newParent = new HuffmanNode(tempLeft, tempRight);
			
			// putting the new node back in the priority queue
			queue.offer(newParent);
		}
		// all one happy tree now
		root = queue.poll();
	}
	
	/*
	 * Constructs a Huffman tree from the Scanner.  
	 * Assumes the Scanner contains a tree description in standard format.
	 */
	public HuffmanTree(Scanner input) {
		// initialize a root for the tree
		root = new HuffmanNode(-1, -1);
		
		while (input.hasNextLine()) {
			// ascii value
			int ascii = Integer.parseInt(input.nextLine());
			// "path" with "directions" (1 = left, 0 = right)
			String path = input.nextLine();
			
			root = buildFromBits(root, ascii, path, 0);
		}
	}
	/*
	 * Helper method for constructing a HuffmanTree from Scanner input
	 * takes the root node as a starting point,
	 * String path as a set of directions (1 = left, 0 = right)
	 * the ascii value of the current character,
	 * and an index value for iterating all of the "directions" in the String path
	 */
	private HuffmanNode buildFromBits(HuffmanNode root, int ascii, String path, int i) {
		if (i == path.length()) {
			root = new HuffmanNode(ascii, -1);
		} else {
			int bit = Character.getNumericValue(path.charAt(i));
			
			if (bit == 1) {
				// build to the left
				if (root.left == null) {
					root.left = new HuffmanNode(-1, -1);
				} 
				// recurse on the left 
				root.left = buildFromBits(root.left, ascii, path, i + 1);
			} else if (bit == 0) {
				// build to the right
				if (root.right == null) {
					root.right = new HuffmanNode(-1, -1);
				}
				// recurse on the right
				root.right = buildFromBits(root.right, ascii, path, i + 1);
			}
		}
		return root;
	}
	
	/*
	 * Reads bits from the given input stream and writes the corresponding characters to the output. 
	 * Stops reading when it encounters a character with value equal to eof. 
	 * This is a pseudo-eof character, so it should not be written to the output file.  
	 * Assumes the input stream contains a legal encoding of characters for this tree’s Huffman code.
	 */
	public void decode(BitInputStream input, PrintStream output, int eof) {
		boolean end = false;
		while (!end) {
			// get an ascii from helpful helper method
			int ascii = returnAsciiFromBits(input, root);
			if (ascii == eof) {
				end = true;
			} else {
				// write to output
				output.write(ascii);
			}
		}
	}
	
	/*
	 * Helper method for decode
	 * Reads bits from input and traverses tree from the root node,
	 * Traversing left or right until it reaches a leaf
	 */
	private int returnAsciiFromBits(BitInputStream input, HuffmanNode root) {
		while(root.left != null && root.right != null) {	// reached a leaf
			if (input.readBit() == 1) {
				root = root.left;
			} else {
				root = root.right;
			}
		}
		return root.getAscii();
	}
	/*
	 * Method writes a .code file to output
	 * the .code file acts like a dictionary, 
	 * correlating ascii characters to a "path" of 1s/0s
	 */
	public void write(PrintStream output) {
		writeCodes(root, "", output);
	}

	/*
	 * Helper method for write()
	 * Traverses the tree starting at the root, 
	 * Then follows the path and writes 1 or 0 depending 
	 * the first line is the most frequent ascii character,
	 * the second line is that character's "path",
	 * the third line is the second most frequent character, and so on
	 */
	private void writeCodes(HuffmanNode root, String path, PrintStream output){
		// in this case we have reached a leaf		
		if (root.left == null && root.right == null && root.getAscii() != 0) {
			output.println(root.getAscii());
			output.println(path);
			return;
		} else {
			writeCodes(root.left, path + "1", output);
			writeCodes(root.right, path + "0", output);
		}
	}
	
	
}
