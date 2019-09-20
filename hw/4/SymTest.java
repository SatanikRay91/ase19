public interface SymTest {

    public static void main(String[] args) {
        Sym sym = new Sym();
        for(int i=0; i<4; i++) sym.addSymbol("a");
        for(int j=0; j<2; j++) sym.addSymbol("b");
        sym.addSymbol("c");
        double entropy = sym.entropyCalc();
        System.out.println("Entropy is :"+entropy);
    }
}
