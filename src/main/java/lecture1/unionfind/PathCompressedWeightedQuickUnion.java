package lecture1.unionfind;

/**
 * Dynamic Connectivity (ex. Networks) - Week 1 of https://www.coursera.org/learn/introduction-to-algorithms/ <br>
 * 
 * WeightedQuickUnion is a data structure improving upon the QuickUnion data structure such that the root
 * element of the smaller of the two trees being brought together in a union operation is linked to the root
 * element of the larger of the two trees. This ensures the trees thus merged, to be shorter compared to those in
 * QuickUnion, thereby reducing the average time taken to perform a connected check between two elements.
 * 
 * This Path-Compressed variant of the WeightedQuickUnion data structure, flattens the forest as it identifies the root
 * of a given element such that that element, and each element observed from it on the traversal up to the root, is reset
 * to be a direct descendant of its grandparent, instead of its parent. In theory, this isn't as good as being reset to the 
 * root element itself, but in practice it is just as good. In practice, to reset to the root element will require an additional pass
 * through the path traversed, while to reset to the grandparent as the direct ancestor, requires just an extra line of code! 
 * 
 * @author Sudarshan Thitte
 */

public class PathCompressedWeightedQuickUnion {
    
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
    public PathCompressedWeightedQuickUnion (int N) {
        connections = treeSize = new int [N];
        for (int i = 0; i < N; ++i) {
            connections[i] = i;
            treeSize[i] = 1;
        }
    }
    
    /**
     * Yield the root element of the input element {@code x}. Identification of the root element is a traversal across the tree of the connected component
     * starting from the input element {@code x}, then on to its parent, and then on to its grandparent and so on, until the element in consideration then is 
     * its own parent which signifies that this element is the root of the tree to which this input element {@code x} belongs <br>
     * To speed up our traversal towards the root, we compress the remaining path waiting to be traversed, by traversing across grandparents instead of parents
     * starting from the element node sent in by the caller. In practice, this is just as good as jumping directly to the root when encountering an already observed
     * element from a prior traversal <br>
     * O(N) = log(N) <base 2> <br>
     * @param x {@code int} element whose root element is to be identified
     * @return {@code int} element which is the root of the input element {@code x}
     */
    public int root (int x) {
        while (connections [x] != x) {
            // compresses the remainder path that needs to be traversed up to the root by hopping across grand parents of each observed node
            // side-effects have no negative impact as future traversals through this node all happen within the same connected component
            connections[x] = connections[connections[x]];
            x = connections [x];
        }
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