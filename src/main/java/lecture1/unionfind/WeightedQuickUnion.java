package lecture1.unionfind;

/**
 * Dynamic Connectivity (ex. Networks) - Week 1 of https://www.coursera.org/learn/introduction-to-algorithms/ <br>
 * 
 * The average distance between a given element and its root element across all trees gives us the
 * height of trees constructed for a given input test case towards QuickUnion or WeightedQuickUnion.
 * 
 * In QuickUnion, trees can be quite tall (measured by the average distance between a given element and its 
 * root, across all trees), thus requiring more time to identify whether two elements are connected by virtue 
 * of a common root element. 
 * 
 * WeightedQuickUnion is a data structure improving upon the QuickUnion data structure such that the root
 * element of the smaller of the two trees being brought together in a union operation is linked to the root
 * element of the larger of the two trees. This ensures the trees thus merged, to be shorter compared to those in
 * QuickUnion, thereby reducing the average time taken to perform a connected check between two elements.
 * 
 * @author Sudarshan Thitte
 */

public class WeightedQuickUnion {
    
    // Array to hold association of an element to its parent element in a connected component. For sake of simplicity, elements are integer numbers.
    // For instance, if connections[5] = 10, it means that element 5 is a child of element 10 and element 10 is the parent of element 5. 
    // Should connections[10] = 10, then the root of both elements 5 and 10 is 10. Each such tree in this data structure is a connected 
    // component where its root is used as its identifier. Knowledge of a given element's root makes the union operation using this data structure 
    // more efficient than in QuickFind while the find operation is a little more involved than in QuickFind
    private int [] connections;
    
    // Array to hold the size of a given tree, rooted at index 'i'. This will be used during the union operation to identify
    // the smaller of the two trees being connected. It also gets updated during that union operation for the root of the tree/forest accepting a new tree. 
    private int [] treeSize;
    
    /**
     * Initialize each element to be its own parent. Initialize the size of each tree, rooted at every element to be 1. <br>
     * O(N) = N <br>
     * @param N {@code int} number of elements whose connections to manage
     */
    public WeightedQuickUnion (int N) {
        connections = treeSize = new int [N];
        for (int i = 0; i < N; ++i) {
            connections[i] = i;
            treeSize[i] = 1;
        }
    }
    
    /**
     * Yield the root element of the input element {@code x}. Identification of the root element is a traversal across the tree of the connected component
     * starting from the input element {@code x}, then on to its parent, and then on to its grandparent and so on, until the element in consideration then is 
     * its own parent which signifies that this element is the root of the input element {@code x} <br>
     * O(N) = log(N) <base 2> <br>
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
     * O(N) = log(N) <base 2><br>
     * @param x first {@code int} element
     * @param y second {@code int} element
     * @return {@code true} should they be connected; {@code false} otherwise  
     */
    public boolean connected (int x, int y) {
        return root(x) == root(y);
    }
    
    /**
     * Connect two disconnected elements and their respective root elements by re-setting the root element of the shorter tree
     * to be a child of the root of the taller tree. For instance, if x's root element is 3 with an enclosing tree height of 4,
     * and y's root element is 4 with an enclosing tree height of 5, then element 3 is made a child of element 4. Such a design
     * keeps the average height of trees represented within this data structure to be smaller in measure. While establishing a 
     * connection, we also update the corresponding size of the larger tree to reflect the new inclusion of all elements from the
     * smaller tree. <br>
     * O(N) = log(N) <base 2><br>
     * @param x first {@code int} element
     * @param y second {@code int} element 
     */
    public void union (int x, int y) {
        if (connected (x,y))
            return;
        else if (treeSize[x] <= treeSize[y]) {
            connections [root(x)] = root (y);
            treeSize [y] += treeSize [x];
        }
        else {
            connections [root(y)] = root(x);
            treeSize [x] += treeSize [y];
        }
    }
}