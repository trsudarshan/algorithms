package lecture1.unionfind.quiz;

/**
 * Add a method 𝚏𝚒𝚗𝚍() to the union-find data type so that 𝚏𝚒𝚗𝚍(𝚒) returns the largest element in the connected component containing i.
 * The operations, 𝚞𝚗𝚒𝚘𝚗(), 𝚌𝚘𝚗𝚗𝚎𝚌𝚝𝚎𝚍(), and 𝚏𝚒𝚗𝚍() should all take logarithmic time or better. 
 * For example, if one of the connected components is {1,2,6,9}, then the 𝚏𝚒𝚗𝚍() method should return 9 for each of the four elements in the connected components.
 * 
 * @author Sudarshan R Thitte
 */
public class CanonicalUnionFind {
    
    // contain association between a member and its connected component's largest element in value
    // we could deem such a large element as also the root of the tree as we establish connections
    int[] connections;
    
    public CanonicalUnionFind (int N) {
        connections = new int[N];
        for (int i = 0; i < N; ++i)
            connections[i] = i;
    }
    
    /**
     * @param x {@code int} value representing the member from whose connected component we're expected to identify the largest element
     * @return largest element in the connected component containing element {@code x}
     */
    public int find (int x) {
        
        while (x != connections[x]) {
            if (x > connections[x])
                connections [x] = x;
            else 
                x = connections[x];
        }
        return x;
    }
    
    /**
     * Connect two members and their associated components together by moving the component of member
     * {@code y} to point to connections [x] or vice-versa depending on which of connections[x] or
     * connections[y] is larger
     * @param x {@code int} value representing a member being connected to another member {@code y}
     * @param y {@code int} value representing a member being connected to another member {@code x}
     */
    public void union (int x, int y) {
        if (connections[x] == connections[y])
            return;
        if (connections [x] < connections [y]) 
            connections [x] = connections [y]; 
        else
            connections [y] = connections [x];
    }
    
}
