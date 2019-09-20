
public class TestZeroR {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZeroR zeroR = new ZeroR();
		zeroR.readFileAndClassify("weathernon.csv");
		zeroR.displayResults();
		System.out.println();
		ZeroR zeroR2 = new ZeroR();
		zeroR2.readFileAndClassify("diabetes.csv");
		zeroR2.displayResults();
		
	}

}
