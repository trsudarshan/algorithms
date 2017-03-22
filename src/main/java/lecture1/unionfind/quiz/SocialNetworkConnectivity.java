package lecture1.unionfind.quiz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import lecture1.unionfind.PathCompressedWeightedQuickUnion;

/**
 * Social Network Connectivity <br>
 * Given a social network containing n members and a log file containing m timestamps at which times pairs of members formed friendships, 
 * design an algorithm to determine the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). 
 * Assume that the log file is sorted by timestamp and that friendship is an equivalence relation. 
 * The running time of your algorithm should be mlogn or better and use extra space proportional to n.
 * 
 * @author Sudarshan R Thitte
 */
public class SocialNetworkConnectivity {
    
    PathCompressedWeightedQuickUnion pcwqc;
    
    // initially, there are no connections
    long firstTimeAllConnected = Long.MIN_VALUE;
    
    public SocialNetworkConnectivity (int N) {
        pcwqc = new PathCompressedWeightedQuickUnion(N);
    }
    
    /**
     * Assume format of each entry in log file to be 'x,y,t' which indicates member x connected with member y at time 't' milliseconds from the time the first 
     * connection in this network was established. We assume the sorting was done in ascending order (earliest connection in the component first). <br>
     * We leverage the path-compressed weighted quick-union algorithm to establish network connectivity across participating members. The tracker on the earliest
     * time towards full network formation is updated only when previously disjoint connected components are now being fused into one larger connected component.
     */
    private void timeWhenAllConnected (String logFilePath) throws FileNotFoundException, IOException {

        // Assuming each entry is on a new line in this file
        BufferedReader reader = new BufferedReader (new FileReader (new File(logFilePath)));
        String entry = null;
        while ((entry = reader.readLine()) != null) {
            String[] entries = entry.split(",");
            int x = Integer.parseInt (entries[0]);
            int y = Integer.parseInt (entries[1]);
            if (!pcwqc.connected(x, y))
                firstTimeAllConnected = Long.parseLong (entries[2]);
            pcwqc.union (x,y);
        }
        reader.close();
        // By this point, all entries in log file would have been read in and all connections established. 
        // We can assume that upon all connections being registered, we're only left with one connected network containing all those connections
        System.out.println("Earliest time all members became connected together : " + firstTimeAllConnected);
    }
    
    public static void main (String args[]) throws Exception {
        if (args.length != 2) {
            System.out.println("ERROR - Provide 2 input arguments. First is integer number of members being connected. Second is String path to log file");
            return;
        }
        SocialNetworkConnectivity s = new SocialNetworkConnectivity(Integer.parseInt(args[0]));
        s.timeWhenAllConnected(args[1]);
    }
}
