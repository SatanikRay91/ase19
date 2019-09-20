
public class TestAbcd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Abcd abcd = new Abcd();
		for(int i=0; i<6; i++) abcd.classify("yes", "yes");
		for(int i=0; i<2; i++) abcd.classify("no", "no");
		for(int i=0; i<5; i++) abcd.classify("maybe", "maybe");
		abcd.classify("maybe", "no");
		abcd.abcdReport();
	}

}
