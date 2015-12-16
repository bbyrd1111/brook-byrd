import java.util.Random;

public class Histogram2 {
    public final double[] freq;   // freq[i] = # occurences of value i
    public double max;            // max frequency of any value
    public double[] data;
    public int count;
    public double maxfr;
    // Create a new histogram. 
    public Histogram2(int N, int T) {
        freq = new double[N + 1]; //number of coins
        data = new double[T]; //number of test
        Random random = new Random();
        this.count = 0;
        for( int t = 0; t < T; t++){
        	//this.addDataPoint(Bernoulli.binomial(N));
        	this.addDataPoint(random.nextInt(N));
        }
        
        maxfr = this.getMaxFr();
        this.normalize();
        
    }

    // Add one occurrence of the value i.
    
    public void addDataPoint(int i) {
        freq[i]++; 
        if (freq[i] > max) max = freq[i]; 
        data[this.count] = i;
        this.count++;
    } 
    
    double getMean(){
    	return StdStats.mean(this.data);
    }
    double getStd(){
    	return StdStats.stddev(this.data);
    }

    double getMaxFr(){
    	double max = 0;
    	for( int i = 0; i < this.freq.length; i++){
    		if(freq[i] > max){
    			max = freq[i];
    		}
    	}
    	return max;
    }
    
    public void normalize(){
    	Histogram normalize = new Histogram(this.freq.length);
    	for( int i = 0; i < this.freq.length; i++){
    	 	freq[i] = freq[i]/this.maxfr;
    	}
    }
    // draw (and scale) the histogram.

    public void drawTop(){
    	StdDraw.setYscale(-3,2);
    	StdStats.plotBars(this.freq);
    }
    public void drawMiddle(){
    	StdDraw.setYscale(-1.5,3.5);
    	StdStats.plotBars(this.freq);
    }
 
    public void drawBottom(){
    	StdDraw.setYscale(-.05,4.95);
    	StdStats.plotBars(this.freq);
    }
    public void draw() {
        StdDraw.setYscale(-1, 1.2);  // to leave a little border
        StdStats.plotBars(freq);
    }

    public static void main(String[] args){
    	Histogram2 hist = new Histogram2(10,100);
    	StdDraw.setCanvasSize(500, 100);
    	hist.draw();

    	
    }
    
} 

