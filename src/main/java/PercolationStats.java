import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Obtain statistics around independent percolation trials performed on a n-by-n grid
 * @author Sudarshan R Thitte
 */
public class PercolationStats {

    /**
     * Record percolation thresholds observed across all trials performed in this experiment
     */
    double[] percolationThresholds;

    /**
     * Initialize an experiment with <{@code trials}> number of trials to be performed examining percolation across a 
     * <{@code n}>-by-<{@code n}> grid. Tthe threshold of percolation is computed per trial and its statistics are used
     * as measures of this experiment's outcome.
     * @param n {@code int} value representing the order of this trial's grid whose percolation is being examined 
     * @param trials {@code int} value indicating the number of such trials to be performed within this experiment
     * @throws IllegalArgumentException when either (or both) of {@code n} and {@code trials} is (or are) non-positive
     */
    public PercolationStats(int n, int trials) {
        if (n <=0) 
            throw new java.lang.IllegalArgumentException ("Grid order must be a non-zero positive value.");
        else if (trials <=0) 
            throw new java.lang.IllegalArgumentException ("Number of trials must be a non-zero positive value.");
        else {
            percolationThresholds = new double[trials];   
            for (int trialIx = 0; trialIx < trials; trialIx++) {
                Percolation trial = new Percolation(n);
                do {
                    // identify site to open
                    int siteOrder = trial.site.length;
                    int gridIx = StdRandom.uniform(0, siteOrder);
                    trial.open((gridIx/siteOrder)-1, (gridIx%siteOrder)-1);
                } while (!trial.percolates());
                // record percolation threshold of current trial
                percolationThresholds [trialIx] = trial.numOpenSites / (trial.site.length^2);
            }
        }
    }

    /**
     * Yield mean of percolation thresholds recorded from across all trials performed thus far
     * @return {@see StdStats#mean(double[])}
     */
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }                          
    
    /**
     * Yield sample standard deviation of percolation threshold observed across all trials performed thus far
     * @return {@see StdStats#stddev(double[])}
     */
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }
    
    /**
     * Yield the low point of the 95% confidence interval of the percolation thresholds' distribution
     */
    public double confidenceLo() {
        return mean() - 1.96*stddev();
    }
    
    /**
     * Yield the high point of the 95% confidence interval of the percolation thresholds' distribution
     */
    public double confidenceHi() {
        return mean() + 1.96*stddev();
    }

    public static void main(String[] args) {
        PercolationStats experiment = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + experiment.mean());
        System.out.println("stddev                  = " + experiment.stddev());
        System.out.println("95% confidence interval = [" + experiment.confidenceLo() + ", " + experiment.confidenceHi() + "]");
    }
}