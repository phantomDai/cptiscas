package mutants.FineGrainedHeap.source;

/**
 * Unbounded priority queue interface
 * @author Maurice Herlihy
 */
public interface PQueue<T> {

    void add(T item, int priority) throws InterruptedException;

    T removeMin();

}

