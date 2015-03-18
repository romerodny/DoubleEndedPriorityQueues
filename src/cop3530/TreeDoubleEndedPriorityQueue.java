package cop3530;

import java.util.Comparator;

/**
 * This class implements a double ended priority queue using a binary search
 * tree
 *
 * @author David Romero PID: 3624439
 */
public class TreeDoubleEndedPriorityQueue<AnyType> implements
        DoubleEndedPriorityQueue<AnyType>
{
    private Node<AnyType> root = null;      //First node
    private Comparator<? super AnyType> cmp;//Comparator for any type of data
    private int size;                       //Height of the tree

    /**
     * Constructor for the Tree Double-ended priority queue
     * 
     */
    public TreeDoubleEndedPriorityQueue()
    {
        this(null);
    }

    /**
     * Constructor for the Tree Double-ended priority queue. Accepts a
     * comparator for specific comparisons
     * 
     * @param c The comparator to be passed 
     */
    public TreeDoubleEndedPriorityQueue(Comparator<? super AnyType> c)
    {
        cmp = c;
        makeEmpty();
    }

    /**
     * Creates a node for the tree
     *
     * @param <AnyType> Generic variable. Node can hold any value
     */
    private static class Node<AnyType>
    {

        private Node<AnyType> left;         //The left node
        private Node<AnyType> right;        //The right node
        private ListNode<AnyType> items;    //The data (using lists for this)

        //The list that hold the data within the node. Singlely linked
        private static class ListNode<AnyType>
        {

            private AnyType data;           //Data in the node
            private ListNode<AnyType> next; //The node next to current node

            /**
             * Creates a list containing data
             *
             * @param d The data
             * @param n The node next to the current node
             */
            public ListNode(AnyType d, ListNode<AnyType> n)
            {
                data = d;
                next = n;
            }

            /**
             * Prints the linked list inside the tree node
             * 
             * @return The list
             */
            public String toString()
            {
                StringBuilder sb = new StringBuilder();

                for (ListNode<AnyType> l = this; l != null; l = l.next)
                {
                    sb.append(l.data);
                    sb.append(" ");
                }

                return new String(sb);
            }
        }

        /**
         * Node constructor
         *
         * @param data Data to be put in the tree
         */
        public Node(AnyType data)
        {
            left = right = null;
            items = new ListNode<AnyType>(data, null);
        }
    }

    /**
     * Compares two pieces of data. If the data is not comparable, cast it to
     * comparable and return the results.
     *
     * @param lhs The first piece data being used to compare
     * @param rhs The second piece of data being used to compare
     * @return -1 if lhs is less than rhs, 1 if it's greater, 0 if it is equal
     */
    public int myCompare(AnyType lhs, AnyType rhs)
    {
        if (cmp == null)
        {
            return ((Comparable) lhs).compareTo(rhs);
        }

        return cmp.compare(lhs, rhs);
    }

    /**
     * Clears the tree
     */
    @Override
    public void makeEmpty()
    {
        root = null;
        size = 0;
    }

    /**
     * Adds new nodes to the tree in sorted order
     *
     * @param x Data to be added
     */
    @Override
    public void add(AnyType x)
    {
        root = add(x, root);
    }

    /**
     * Private add recursive routine for the tree.Called by public add
     * 
     * @param x The data to be added
     * @param n The starting point of the search (root)
     * @return The added node
     */
    private Node add(AnyType x, Node<AnyType> n)
    {
        //If the position where the node should be is null, add it there
        if (n == null)
        {
            ++size;
            return new Node(x);
        }

        //If the incoming data is smaller than the current node, go left
        if (myCompare(n.items.data, x) > 0)
        {
            n.left = add(x, n.left);
        }
        //Else if the incoming data is larger, go to the right
        else if (myCompare(n.items.data, x) < 0)
        {
            n.right = add(x, n.right);
        }
        //If the data is the same as the current nodes, add it to the list in
        //the node
        else if (myCompare(n.items.data, x) == 0)
        {
            n.items = new Node.ListNode<>(x, n.items);
        }

        return n;
    }

    /**
     * Deletes the smallest value. Also handle case where root is smallest
     *
     * @return The value deleted
     */
    @Override
    public AnyType deleteMin()
    {
        //If the list is empty, you can't remove anything!
        if (isEmpty())
        {
            throw new UnderflowException();
        }

        //If you can't go anymore left, this must be the smallest
        if (root.left == null)
        {
            //Holds the data that's to be removed
            AnyType rem = root.items.data;

            //If the root has duplicates, only remove one of the duplicates
            if (root.items.next != null)
            {
                root.items = root.items.next;
                return rem;
            }
            //Else the node has no duplicates, therefore simply remove the node
            else
            {
                root = root.right;
                --size;
                return rem;
            }
        }
        else //You can still travel further left
        {
            return deleteMin(root);
        }
    }

    /**
     * Private delete min recursive routine. Handles case where there are left 
     * nodes
     * 
     * @param n Starting node for search (root)
     * @return The value that is removed
     */
    private AnyType deleteMin(Node<AnyType> n)
    {
        //"Planning ahead". Checking if there is an upcoming null node in order
        //to adjust links accordingly
        if (n.left.left == null)
        {
            //Holds the data that's to be removed
            AnyType rem = n.left.items.data;

            //Checking if the tree node has duplicates, if it does remove one
            //of the duplicates
            if (n.left.items.next != null)
            {
                n.left.items = n.left.items.next;
                return rem;
            }
            else //There are no duplicates. Remove the node
            {
                n.left = n.left.right;
                --size;
                return rem;
            }
        }
        else //Else keep going more left
        {
            return deleteMin(n.left);
        }
    }

    /**
     * Deletes the largest value. Also handles the root case
     * 
     * @return The value removed
     */
    @Override
    public AnyType deleteMax()
    {
        //If the list is empty, you can't remove anything!
        if (isEmpty())
        {
            throw new UnderflowException();
        }
        
        //Checking if the root has a right path, if not root is max
        if (root.right == null)
        {
            //Holds the data that's to be removed
            AnyType rem = root.items.data;

            //If the root has duplicates, remove one of them
            if (root.items.next != null)
            {
                root.items = root.items.next;
                return rem;
            }
            else //The root node has no duplicates, remove
            {
                root = root.left;
                --size;
                return rem;
            }
        }
        else //Root does have a right path
        {
            return deleteMax(root);
        }
    }

    /**
     * Private recursive routine. Handles case where there is a right path
     * 
     * @param n Starting point (root)
     * @return The value deleted
     */
    private AnyType deleteMax(Node<AnyType> n)
    {
        //"Planning ahead". Checking the path ahead to see if there is an
        //upcoming null node inorder to adjust links accordingly
        if (n.right.right == null)
        {
            //Holds the data that's to be removed
            AnyType rem = n.right.items.data;

            //Checking if the tree node has any duplicates
            if (n.right.items.next != null)
            {
                n.right.items = n.right.items.next;
                return rem;
            }
            else //If it doesn't just remove the node
            {
                n.right = n.right.left;
                --size;

                return rem;
            }
        }
        else //Can keep going more left
        {
            return deleteMax(n.right);
        }
    }

    /**
     * Finds the smallest value in the tree
     * 
     * @return The smallest value
     */
    @Override
    public AnyType findMin()
    {
        if (isEmpty())
        {
            throw new UnderflowException();
        }
        return findMin(root);
    }

    /**
     * Private recursive routine for find min. Flows the left most path
     * 
     * @param n The starting node
     * @return The smallest value
     */
    private AnyType findMin(Node<AnyType> n)
    {
        if (n.left == null)
        {
            return n.items.data;
        }

        return findMin(n.left);
    }

    /**
     * Finds the largest value in the tree
     * 
     * @return The largest value 
     */
    @Override
    public AnyType findMax()
    {
        if (root == null)
        {
            throw new UnderflowException();
        }

        return findMax(root);
    }

    /**
     * Private recursive routine for find max. Follows right most path
     * 
     * @param n Starting node 
     * @return Largest value
     */
    private AnyType findMax(Node<AnyType> n)
    {
        if (n.right == null)
        {
            return n.items.data;
        }

        return findMax(n.right);
    }

    /**
     * Checks if the tree is empty
     * 
     * @return Boolean showing whether tree is empty or not
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Prints the tree. Calls a private recursive routine to print the leaves
     * of the tree
     * 
     * @return The printed tree
     */
    public String toString()
    {
        //If the tree is empty, just return an empty brace
        if (isEmpty())
        {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[ ");
        toString(root, sb);
        sb.append("]");

        return new String(sb);
    }

    /**
     * Private recursive routine for toString. Follows the left and right paths
     * and prints the contents
     * 
     * @param t The starting node
     * @param sb Stringbuilder going to be used to append
     */
    private void toString(Node<AnyType> t, StringBuilder sb)
    {
        if (t != null)
        {
            toString(t.left, sb);
            sb.append(t.items);
            toString(t.right, sb);
        }
    }
}
