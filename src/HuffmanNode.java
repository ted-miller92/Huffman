import java.util.Map;

public class HuffmanNode implements Comparable<HuffmanNode> {
	public int frequency;
	public int ascii;
	public HuffmanNode left;
	public HuffmanNode right;
	
	// constructor given ascii value and frequency
	public HuffmanNode(int ascii, int frequency) {
		this.ascii = ascii;
		this.frequency = frequency;
	}
	
	// constructor for combining two nodes
	public HuffmanNode(HuffmanNode left, HuffmanNode right) {
		this.left = left;
		this.right = right;
	}
	
	public void setLeftChild(HuffmanNode left) {
		this.left = left;
	}
	public void setRightChild(HuffmanNode right) {
		this.right = right;
	}
	public int getFrequency() {
		return frequency;
	}
	public int getAscii() {
		return ascii;
	}
	public void setAscii(int ascii) {
		this.ascii = ascii;
	}
	
	@Override
	public int compareTo(HuffmanNode that) {
		return this.frequency - that.frequency;
	}
}