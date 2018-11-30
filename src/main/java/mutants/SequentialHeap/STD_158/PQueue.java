package mutants.SequentialHeap.STD_158;

/**
 * Unbounded priority queue interface
 * @author Maurice Herlihy
 */
public interface PQueue<T> {

    void add(T item, int priority);

    T removeMin();

}

