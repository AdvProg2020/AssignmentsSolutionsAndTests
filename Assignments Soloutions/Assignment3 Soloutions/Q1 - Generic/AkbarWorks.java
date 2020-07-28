import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AkbarWorks<T extends Comparable> {
    private ArrayList<T> items = new ArrayList<>();
    private ArrayList<T> removedItems = new ArrayList<T>();


    private void removeItem(int index) {
        removedItems.add(items.get(index));
        items.remove(index);
    }
    private void removeItem(T item) {
        removedItems.add(item);
        items.remove(item);
    }

    public void add(T item) {
        items.add(item);
    }

    public T getMin() throws IllegalStateException {
        if( items.isEmpty() )
            throw new IllegalStateException();
        T item = (T)Collections.min(items);
        removeItem(item);
        return item;
    }

    public T getLast(boolean remove) throws IllegalStateException {
        if( items.isEmpty() )
            throw new IllegalStateException();
        T ret = items.get(items.size() - 1);
        if( remove )
            removeItem(items.size() - 1);
        return ret;
    }

    public T getFirst(boolean remove) throws IllegalStateException {
        if( items.isEmpty() )
            throw new IllegalStateException();
        T ret = items.get(0);
        if( remove )
            removeItem(0);
        return ret;
    }

    public Comparable[] getLess(T element, boolean remove) {
        ArrayList<T> ret = new ArrayList<T>();
        for(T item: items)
            if(item.compareTo(element) < 0)
                ret.add(item);
        if(remove) {
            for(T item: ret)
                removeItem(item);
        }

        return ret.toArray(new Comparable[ret.size()]);
    }

    public Comparable[] getRecentlyRemoved(int n) {
        if(n > removedItems.size() )
            n = removedItems.size();


        List<T> orig = removedItems
                .subList(removedItems.size() - n, removedItems.size() );
        List<T> ret = new ArrayList<T>(orig);
        Collections.reverse(ret);
        return ret.toArray(new Comparable[0]);
    }
}
 