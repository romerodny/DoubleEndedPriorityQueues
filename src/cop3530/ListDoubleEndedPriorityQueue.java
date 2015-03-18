package cop3530;

import java.util.Comparator;

/**
 * This class implements a double ended priority queue using a linked list
 *
 * @author David Romero PID: 3624439
 */
public class ListDoubleEndedPriorityQueue<AnyType> implements
        DoubleEndedPriorityQueue<AnyType>
{
    private Comparator<? super AnyType> cmp;//Comparator for any type of data
    private Node<AnyType> first = null;     //First node
    private Node<AnyType> last = null;      //Last node
    private int size = 0;                   //The size of the list

    /**
     * Creates a node for the linked list
     *
     * @param <AnyType> Generic class; node can take any type of data
     */
    private static class Node<AnyType>
    {
        private AnyType data;               //The data within the node
        private Node<AnyType> next;         //Next node
        private Node<AnyType> previous;     //Previous node

        /**
         * Creates a node for the linked list
         *
         * @param x The data stored within the node
         * @param p The node before present node
         * @param n The node next to present node
         */
        public Node(AnyType d, Node<AnyType> p, Node<AnyType> n)
        {
            data = d;
            previous = p;
            next = n;
        }
    }

    /**
     * This is the list constructor
     */
    public ListDoubleEndedPriorityQueue()
    {
        this(null);
    }

    /**
     * Constructs a list object that accepts a different comparator
     *
     * @param c Comparator that will be used
     */
    public ListDoubleEndedPriorityQueue(Comparator<? super AnyType> c)
    {
        cmp = c;
        makeEmpty();
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
     * Empties the entire list
     *
     */
    @Override
    public void makeEmpty()
    {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Adds node to the front of the linked list. (Created to avoid duplicate
     * code)
     * 
     * @param x Data to be added
     */
    private void addFront(AnyType x)
    {
        first = new Node(x, null, first);
        first.next.previous = first;
        ++size;
    }

    /**
     * Adds node to the end of the linked list. (Created to avoid duplicate 
     * code)
     * 
     * @param x Data to be added
     */
    private void addEnd(AnyType x)
    {
        last = new Node(x, last, null);
        last.previous.next = last;
        ++size;
    }

    /**
     * Adds data to the linked list
     *
     * @param x data that is to be added
     */
    @Override
    public void add(AnyType x)
    {
        //If the list is empty, both the first and last node are 
        //the same node
        if (isEmpty())
        {
            first = last = new Node(x, null, null);
            ++size;
        }
        //If there is already a node in the list
        else if (size == 1)
        {
            //Checking if the new node entered is smaller than the existing node
            if (myCompare(first.data, x) >= 0)
            {
                addFront(x);
            }
            //Else just add it to the end of the list
            else if (myCompare(first.data, x) <= 0)
            {
                addEnd(x);
            }

        }
        //Checking if the data coming in is smaller than the first
        else
        {
            if (myCompare(first.data, x) >= 0)
            {
                addFront(x);
            }
            //Checking if the last data is smaller than the new data
            else if (myCompare(last.data, x) <= 0)
            {
                addEnd(x);
            }
            else
            {
                //Placing data within the list when there is already existing 
                //data. Stepping through the list node by node, placing it in 
                //the right place
                for (Node<AnyType> f = first.next; f != null; f = f.next)
                {
                    //Checking if the data is smaller than the next node
                    if (myCompare(x, f.data) < 0)
                    {
                        f.previous = f.previous.next = new Node(x, f.previous, f);
                        ++size;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Deletes the smallest value in the list
     *
     * @return The value removed
     */
    @Override
    public AnyType deleteMin()
    {
        //Checking if the list is empty, can't remove if it is!
        if (isEmpty())
        {
            throw new UnderflowException();
        }
        
        //Checking if the list only has one element inside, just make empty
        else if (size == 1)
        {
            --size;
            AnyType rem = first.data;
            makeEmpty();
            return rem;
        }
        //Else the list has more than one element, grab the front
        else
        {
            AnyType rem = first.data;
            first = first.next;
            first.previous = null;
            --size;
            return rem;
        }
    }

    /**
     * Deletes the largest value in the list
     * 
     * @return The value removed
     */
    @Override
    public AnyType deleteMax()
    {
        //Checking if the list is empty, can't remove if it is!
        if (isEmpty())
        {
            throw new UnderflowException();
        }
        //Checking if the list only has one element, if it is make empty
        else if (size == 1)
        {
            --size;
            AnyType rem = last.data;
            makeEmpty();
            return rem;
        }
        //Else grab the end and reassin the links accordingly
        else
        {
            --size;
            AnyType rem = last.data;
            last = last.previous;
            last.next = null;
            return rem;
        }
    }

    /**
     * Finds the smallest value in the list
     *
     * @return The smallest value in the list
     */
    @Override
    public AnyType findMin()
    {
        //Can't find a value if the list is empty
        if (isEmpty())
        {
            throw new UnderflowException();
        }

        return first.data;
    }

    /**
     * Finds the largest value in the list
     *
     * @return The largest value
     */
    @Override
    public AnyType findMax()
    {
        //Can't find a value if the list is empty
        if (isEmpty())
        {
            throw new UnderflowException();
        }
        return last.data;
    }

    /**
     * Checks if the list is empty
     *
     * @return Boolean showing whether the list is empty or not
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Prints the list
     *
     * @return The list
     */
    @Override
    public String toString()
    {
        if (isEmpty())
        {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[ ");

        for (Node<AnyType> f = first; f != null; f = f.next)
        {
            sb.append(f.data);
            sb.append(" ");
        }
        sb.append("]");

        return new String(sb);
    }
}
