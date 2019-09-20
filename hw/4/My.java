import java.util.ArrayList;
import java.util.List;

public class My {
	private List<Integer> goals;	// Indices for goals
	private List<Integer> nums;		// Indices for Nums
	private List<Integer> syms;		// Indices for Syms
	private List<Integer> w;		// Indices for neg weights
	private List<Integer> xnums;
	private List<Integer> xs;
	private List<Integer> xsyms;
	
	public My() {
		goals = new ArrayList<>();
		syms = new ArrayList<>();
		nums = new ArrayList<>();
		w = new ArrayList<>();
		xnums = new ArrayList<>();
		xs = new ArrayList<>();
		xsyms = new ArrayList<>();
	}
	
	public void addToList(String listName, Integer value) {
		switch(listName) {
			case "goals": 	goals.add(value);
							break;
			case "nums": 	nums.add(value);
							break;
			case "syms": 	syms.add(value);
							break;
			case "w": 		w.add(value);
							break;
			case "xnums": 	xnums.add(value);
							break;
			case "xs": 		xs.add(value);
							break;	
			case "xsyms": 	xsyms.add(value);
							break;	
		}
	}

	public List<Integer> getGoals() {
		return goals;
	}

	public List<Integer> getNums() {
		return nums;
	}

	public List<Integer> getSyms() {
		return syms;
	}

	public List<Integer> getW() {
		return w;
	}

	public List<Integer> getXnums() {
		return xnums;
	}

	public List<Integer> getXs() {
		return xs;
	}

	public List<Integer> getXsyms() {
		return xsyms;
	}
}
