package lecture1.unionfind;

/**
 * Dynamic Connectivity (ex. Networks) - Week 1 of https://www.coursera.org/learn/introduction-to-algorithms/ <br>
 * 
 * QuickFind is a data structure in which an element is associated with its connected component. 
 * Using this data structure, we define operations to connect two such components together or 
 * establish whether two elements are connected via their association to a common connected component. 
 * 
 * DEFECT - Trees are flat and can be expensive to keep them flat; 'union' operation is quite expensive (N array accesses)
 * 
 * @author Sudarshan Thitte
 */
public class QuickFind {

    // Array to hold association of an element to its connected component. For sake of simplicity, we use integer numbers
    // as objects, and integer numbers as connected component identifiers. For instance, if connections [5] = 10, it means that
    // element 5 is part of connected component 10.
    private int [] connections;
    
    /**
     * Initialize each element to belong to its own connected component <br>
     * O(N) = N <br>
     * @param N {@code int} number of elements whose connections to manage
     */
    public QuickFind(int N) {
        connections = new int [N];
        for (int i = 0; i < N; ++i)
            connections[i] = i;
    }
    
    /**
     * Should two elements belong to the same connected component, then they are connected to each other <br>
     * O(N) = 1 <br>
     * @param x first {@code int} element
     * @param y second {@code int} element
     * @return {@code true} should they be connected; {@code false} otherwise  
     */
    public boolean connected (int x, int y) {
        return connections[x] == connections[y];
    }
    
    /**
     * Connect two disconnected elements and their respective connected components by re-setting an element's and its connections to
     * the other element's connected component identifier. For instance, if x belongs to component 5 and y belongs to component 3, a call
     * to union (x, y) must re-set x and all the other elements in its connected component to be a part of 3 <br>
     * O(N) = N <br>
     * @param x first {@code int} element
     * @param y second {@code int} element 
     */
    public void union (int x, int y) {
        if (connected (x,y))
            return;
        int cx = connections[x];
        int cy = connections[y];
        for (int i = 0; i < connections.length; ++i) {
            if (connections[i] == cx)
                connections[i] = cy;
        }
    }
}