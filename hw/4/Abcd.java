import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class Abcd {
	private Map<String, Integer> aMap;	// Non Feature -> Non Feature
	private Map<String, Integer> bMap;	// Feature -> Non Feature
	private Map<String, Integer> cMap;	// Non Feature -> Feature
	private Map<String, Integer> dMap;	// Feature -> Feature
	private Set<String> featureSet;
	private int num;
	
	public Abcd() {
		featureSet = new HashSet<String>();
		aMap = new HashMap<String, Integer>();
		bMap = new HashMap<String, Integer>();
		cMap = new HashMap<String, Integer>();
		dMap = new HashMap<String, Integer>();
		num = 0;
	}
	
	public void classify(String correctClass, String predictedClass) {
		num++;
		if(correctClass.equals(predictedClass)) {
			featureSet.add(correctClass);
			if(dMap.containsKey(correctClass)) {
				dMap.put(correctClass, dMap.get(correctClass)+1);
			} else {
				dMap.put(correctClass, 1);
				aMap.put(correctClass, 0);
				bMap.put(correctClass, 0);
				cMap.put(correctClass, 0);
				int count = 0;
				for(String k : dMap.keySet()) {
					if(!k.equals(correctClass)) {
						count+= dMap.get(k);
					}
				}
				aMap.put(correctClass, count);
			}
			for(String k : aMap.keySet()) {
				if(!k.equals(correctClass)) {
					aMap.put(k, aMap.get(k)+1);
				}
			}
		} else {
			featureSet.add(correctClass);
			featureSet.add(predictedClass);
			if(bMap.containsKey(correctClass)) {
				bMap.put(correctClass, bMap.get(correctClass)+1);
			} else {
				dMap.put(correctClass, 0);
				aMap.put(correctClass, 0);
				bMap.put(correctClass, 1);
				cMap.put(correctClass, 0);
				int count = 0;
				for(String k : dMap.keySet()) {
					if(!k.equals(correctClass)) {
						count+= dMap.get(k);
					}
				}
				aMap.put(correctClass, count);
			}
			if(bMap.containsKey(predictedClass)) {
				cMap.put(predictedClass, cMap.get(predictedClass)+1);
			} else {
				dMap.put(predictedClass, 0);
				aMap.put(predictedClass, 0);
				bMap.put(predictedClass, 0);
				cMap.put(predictedClass, 1);
				int count = 0;
				for(String k : dMap.keySet()) {
					if(!k.equals(predictedClass)) {
						count+= dMap.get(k);
					}
				}
				aMap.put(predictedClass, count);
			}
			for(String k : aMap.keySet()) {
				if(!k.equals(correctClass) && !k.equals(predictedClass)) {
					aMap.put(k, aMap.get(k)+1);
				}
			}
		}
	}
	
	public void abcdReport() {
		System.out.println(" num\t| a\t| b\t| c\t| d\t| acc\t| prec\t| pd\t| pf\t| f\t| g\t| class");
		System.out.print("=");
		for(int i=0; i<12; i++) {
			for(int j=0; j<7; j++) {
				System.out.print("=");
			}
			if(i<11)
				System.out.print("+");
		}
		System.out.println("");
		for(String k : featureSet) {
			int a = aMap.get(k);
			int b = bMap.get(k);
			int c = cMap.get(k);
			int d = dMap.get(k);
			
			float acc = accuracy(a, d, num);
			float recall = recall(b, d);
			float fAlarm = falseAlarm(a, c);
			float prec = precision(c, d);
			float f = fMeasure(prec, recall);
			float g = gMeasure(fAlarm, recall);
			System.out.println(" "+num+"\t| "+(a == 0 ? "" : a)+
					"\t| "+(b == 0 ? "" : b)+
					"\t| "+(c == 0 ? "" : c)+
					"\t| "+(d == 0 ? "" : d)+
					String.format("\t| %.2f"+
					"\t| %.2f"+
					"\t| %.2f"+
					"\t| %.2f"+
					"\t| %.2f"+
					"\t| %.2f"+
					"\t| %s", acc, prec, recall, fAlarm, f, g, k));
		}
	}
	
	private float accuracy(int a, int d, int num) {
		float acc = (float)(a+d)/num;
		return acc;
	}
	
	private float recall(int b, int d) {
		return (float)(d)/(b+d);
	}
	
	private float precision(int c, int d) {
		return (float)d/(c+d);
	}
	
	private float falseAlarm(int a, int c) {
		return (float)c/(a+c);
	}
	
	private float fMeasure(float prec, float recall) {
		if(prec == 0.0f && recall == 0.0f) {
			return 0.0f;
		} else {
			return 2*prec*recall/(prec+recall);
		}
	}
	
	private float gMeasure(float falseMeasure, float recall) {
		if(falseMeasure == 0.0f && recall == 0.0f) {
			return 0.0f;
		} else {
			return 2*(1-falseMeasure)*recall/(1-falseMeasure+recall);
		}
	}
	
	
	
	
}
