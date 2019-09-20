
public class TestNb {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Nb nb  = new Nb();
		nb.readFileAndClassify("weathernon.csv");
		nb.displayResults();
		System.out.println();
		
		Nb nb2 = new Nb();
		nb2.readFileAndClassify("diabetes.csv");
		nb2.displayResults();
		
	}

}
