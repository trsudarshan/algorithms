package lecture1.unionfind;

/**
 * Dynamic Connectivity (ex. Networks) - Week 1 of https://www.coursera.org/learn/introduction-to-algorithms/ <br>
 * 
 * QuickUnion is a data structure in which an element is associated with its parent element. Using this data structure, we define
 * operations to connect two such elements together or establish whether two elements are connected via their
 * association to a common connected component, in terms of whether they both share the same root element. The root element
 * of a given element is defined as the final element in a traversal starting at the given element and on wards to its parent
 * element, grand parent element and so on. An element being its own parent, signifies that it is the root element of its tree.
 * 
 * DEFECT - Trees can get tall; 'connected' operation could be quite expensive (ex. N array accesses)
 * 
 * @author Sudarshan Thitte
 */
public class QuickUnion {
    
    // Array to hold association of an element to its parent element in a connected component. For sake of simplicity, elements are integer numbers.
    // For instance, if connections[5] = 10, it means that element 5 is a child of element 10 and element 10 is the parent of element 5. 
    // Should connections[10] = 10, then the root of both elements 5 and 10 is 10. Each such tree in this data structure is a connected 
    // component where its root is used as its identifier. Knowledge of a given element's root makes the union operation using this data structure 
    // more efficient than in QuickFind while the find operation is a little more involved than in QuickFind
    private int [] connections;
    
     /**
     * Initialize each element to be its own parent <br>
     * O(N) = N <br>
     * @param N {@code int} number of elements whose connections to manage
     */
    public QuickUnion (int N) {
        connections = new int [N];
        for (int i = 0; i < N; ++i)
            connections[i] = i;
    }
    
    /**
     * Yield the root element of the input element {@code x}. Identification of the root element is a traversal across the tree of the connected component
     * starting from the input element {@code x}, then on to its parent, and then on to its grandparent and so on, until the element in consideration then is 
     * its own parent which signifies that this element is the root of the input element {@code x} <br>
     * O(N) = N <br>
     * @param x {@code int} element whose root element is to be identified
     * @return {@code int} element which is the root of the input element {@code x}
     */
    public int root (int x) {
        while (connections [x] != x)
            x = connections [x];
        return x;
    }
    
    /**
     * Should two elements lead to the same root element, then they are connected to each other <br>
     * O(N) = N <br>
     * @param x first {@code int} element
     * @param y second {@code int} element
     * @return {@code true} should they be connected; {@code false} otherwise  
     */
    public boolean connected (int x, int y) {
        return root(x) == root(y);
    }
    
    /**
     * Connect two disconnected elements and their respective root elements by re-setting the root element of the first element
     * to be a child of the root of the second element. For instance, if x's root is element 5 and y's root is element 3, a call
     * to union (x, y) must make element 5 a child of element 3 <br>
     * O(N) = N <br>
     * @param x first {@code int} element
     * @param y second {@code int} element 
     */
    public void union (int x, int y) {
        if (connected (x,y))
            return;
        connections [root(x)] = root (y);
    }
}
