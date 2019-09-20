import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Num extends Col {

    private List<Float> valList;
    private List<Double> meanList;
    private List<Double> sdList;
    private Float prevSum;
    private Integer count;
    private Double mean;
    private Double stdDev;
    private Float hi;
    private Float low;
    
    public Num() {
    	valList = new ArrayList<>();
        meanList = new ArrayList<>();
        sdList = new ArrayList<>();
        prevSum = 0.0f;
        count = 0;
        hi = Float.MIN_VALUE;
        low = Float.MAX_VALUE;
    }
    
    public void updateMeanAndSD(Float arg) throws IOException {
        valList.add(arg);
        count++;
        prevSum += arg;
        if(arg > hi) hi = arg;
        if(arg < low) low = arg;
        mean = generateMean(prevSum, count);
        stdDev = generateSD(valList, mean, count);
        //System.out.println("ValList: "+valList+", mean: "+mean+", count: "+count);
        //System.out.println("SD: "+stdDev);
        if(count % 10 == 0){
            meanList.add(mean);
            sdList.add(stdDev);
        }

    }
    public Integer getCount() {
		return count;
	}

	public Double getMean() {
		return mean;
	}

	public Double getStdDev() {
		return stdDev;
	}

	public Float getHi() {
		return hi;
	}

	public Float getLow() {
		return low;
	}

	public void deleteNum() throws IOException {
        Float arg = valList.get(valList.size()-1);
        valList.remove(valList.size()-1);
        prevSum-=arg;
        count--;
        if(count%10 == 0){
            double mean = generateMean(prevSum, count);
            if(meanList.get((count/10)-1).equals(mean)) {
               // System.out.println("Mean matches at count "+count);
            }
        }
    }
    
    private List<Integer> generateList() {
        Random random = new Random();
        List<Integer> integerList = new ArrayList<>();
        return integerList;
    }

    private double generateMean(double prevSum, int count) {
        return (prevSum/count);
    }

	private double generateSD(List<Float> integerList, double mean, int count) {
        float variance = 0;
        if(count > 1) {
	        for (int i = 0; i < count; i++) {
	            variance += Math.pow(integerList.get(i)-mean, 2);
	        }
	        return Math.sqrt(variance/(count-1));
	    } else {
	    	return 0;
	    }
    }
	
	 public float numLike(float val) {
		//System.out.println("Mean is "+mean+" and SD is "+stdDev); 
        float numerator = (float) Math.pow(2.7128, (-Math.pow(val - mean, 2)/(2*Math.pow(stdDev, 2) + 0.0001)));
        //System.out.println(numerator);
        float denominator = (float) Math.pow(3.14159*2*Math.pow(stdDev, 2), 0.5);
        //System.out.println(denominator);
        return (float) (numerator/(denominator + Math.pow(10, -64)));
    }

}
