package cop3530;

/**
 * Double-Ended priority queue interface. Used for both the list and tree
 * implementations
 * 
 * @author David Romero PID: 3624439
 */
public interface DoubleEndedPriorityQueue<AnyType>
{
    void makeEmpty();
    void add ( AnyType x );
    AnyType deleteMin( );
    AnyType deleteMax( );
    AnyType findMin( );
    AnyType findMax( );
    boolean isEmpty( );
}
