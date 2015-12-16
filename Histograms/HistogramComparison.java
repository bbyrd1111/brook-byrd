import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.TTest;

public class HistogramComparison{
	Histogram2 a;
	Histogram2 b;
	private int N;
	private int T;
	ArrayList<Double> diff = new ArrayList<Double>();
	private double mean = 0;
	private int sum = 0;
	private double std = 0;
	private Histogram2 comparison;


	//Constructor
	public HistogramComparison(Histogram2 hist1, Histogram2 hist2){
		a = hist1;
		b = hist2;
		a.freq[50] = 0;
		a.freq[60] = 0;
        this.N = hist1.freq.length - 1;   // number of coins
        this.T = hist1.data.length;   // number of trials 
  
        this.comparison = new Histogram2(N, T);
        for( int i = 0; i < hist1.freq.length; i++){
        	Double d = new Double(Math.abs(hist1.freq[i] - hist2.freq[i]));
        	comparison.freq[i] = d;
        	sum += d;
        	diff.add(d);
        }
        
        double[] array = new double[diff.size()];
        for( int i = 0; i < diff.size(); i++){
        	array[i] = diff.get(i);
        }
        
        this.mean = sum/(double)comparison.freq.length;
        this.std = StdStats.stddev(array);
  
	}
	
	public ArrayList<Integer> getDifferingFreq(double alpha){
		ArrayList<Integer> possible = new ArrayList<Integer>();
		for( int i = 0; i < this.comparison.freq.length; i++){
			if(this.comparison.freq[i] > ((1-alpha)*(b.freq[i]))){////really want to put refference histogram here				
				possible.add(i);
				
			}
		}
		return possible;
	}
	
	public double getMean(){
		return this.mean;
	}
	
	public double getStd(){
		return this.std/Math.sqrt(this.N);
	}
	public String MeanPercentTest(double alpha){
		if(Math.abs((a.getMean()/b.getMean())-1) > alpha){
			return "reject";		
		}
		else{
			return "pass";
		}
	}
	public double MeanPercent(){
		return (Math.abs((a.getMean()/b.getMean())-1));
	
	}
	public double StdPercent(){
		return (Math.abs((a.getStd()/b.getStd())-1));
	
	}
	public String StdPercentTest(double alpha){
		if(Math.abs((a.getStd()/b.getStd())-1) > alpha){
			return "reject";		
		}
		else{
			return "pass";
		}
	}
	
	String TwoPairedTTest(double alpha){
		TTest test = new TTest();
		if(!test.pairedTTest(this.a.freq,this.b.freq, alpha)){
			return "pass";			
		}
		else{
			return "reject";
		}
		
	}
	
	double TwoPairedTStat(){
		TTest test = new TTest();
		return test.pairedT(this.a.freq,this.b.freq);	
		
	}
	
	
	public void show(){
		
		StdDraw.setCanvasSize(1000, 650);
		StdDraw.text(.38,.25, String.format("Mean Comparison: %f  Std Comparison: %3.2f", this.getMean(), this.getStd()));      
		StdDraw.text(.25, .85, String.format("Mean A: " + this.a.getMean() + " Std A: %.2f", this.a.getStd()));
		StdDraw.text(.25, .55, String.format("Mean B: " + this.b.getMean() + " Std B: %.2f", this.b.getStd()));
		StdDraw.text(.2,.21, String.format("Tstat: %3.2f Ttest: %s", this.TwoPairedTStat(),this.TwoPairedTTest(.01)));
		StdDraw.text(.3,.17, String.format("Mean Percent: %3.3f Mean Test: %s", this.MeanPercent(),this.MeanPercentTest(.05)));
		StdDraw.text(.29,.13, String.format("Std Percent: %3.3f Std Test: %s", this.StdPercent(),this.StdPercentTest(.05)));
		StdDraw.text(.34,.09, "Major differing frequence at N=" + this.getDifferingFreq(.05));///saying the difference is larger than 95% of the referrence
		// display using standard draw
		this.a.drawTop();
		this.b.drawMiddle();
		this.comparison.drawBottom();
		//***
	}
        
        	
  public static void main(String[] args){
  		  Histogram2 h1 = new Histogram2(100,100000);
  		  Histogram2 h2 = new Histogram2(100, 100000);
  		  HistogramComparison test = new HistogramComparison(h1,h2);
  		  System.out.println("h1 mean:  " + h1.getMean());
  		  System.out.println("h1 std: " + h1.getStd());
  		  System.out.println("h2 mean: " + h2.getMean());
		  System.out.println("h2 std: " +  h2.getStd());
		  System.out.println("test mean:  " + test.getMean());
  		  System.out.println("test std: " + test.getStd());
  		  System.out.println("Mean percent test: " + test.MeanPercentTest(.05));
  		  System.out.println("Std percent test: " + test.StdPercentTest(.05));
  		  System.out.println("TTest: " + test.TwoPairedTTest(.01));
  		  System.out.println(test.getDifferingFreq(.05));
  		  test.show();
  	  }






	

}
