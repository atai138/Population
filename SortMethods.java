import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * SortMethods - Sorts an ArrayList of City with a variety of
 * sorts possible.
 *
 * @author Alan Tai
 * @since January 16, 2022
 */
public class SortMethods {
    /**
     * Swaps two City objects in array arr
     * 
     * @param arr array of City objects
     * @param x   index of first object to swap
     * @param y   index of second object to swap
     */
    private void swap(List<City> arr, int x, int y) {
        City temp = arr.get(x);
        arr.set(x, arr.get(y));
        arr.set(y, temp);
    }

    /**
     * Selection Sort algorithm
     * 
     * @param arr   array of City objects to sort
     * @param comp  comparator to sort City objects
     * @return      time it took to sort in milliseconds
     */
    public long selectionSort(List<City> arr, Comparator<City> comp) {
        long startMillisec = System.currentTimeMillis();
        for (int outer = arr.size(); outer > 1; outer--) {
            int maxIndex = 0;
            for (int inner = 1; inner < outer; inner++) {
                if (comp.compare(arr.get(inner), arr.get(maxIndex)) > 0)
                    maxIndex = inner;
            }
            swap(arr, maxIndex, outer - 1);
        }
        long endMillisec = System.currentTimeMillis();
        return endMillisec - startMillisec;
    }

    /**
     * Insertion Sort algorithm
     * 
     * @param arr   array of City objects to sort
     * @param comp  comparator to sort City objects
     * @return      time it took to sort in milliseconds
     */
    public long insertionSort(List<City> arr, Comparator<City> comp) {
        long startMillisec = System.currentTimeMillis();
        int size = arr.size();
        for (int n = 1; n < size; n++) {
			City placeThis = arr.get(n);
            int i;
            for (i = n; i > 0 && comp.compare(placeThis, arr.get(i - 1)) < 0; i--) {
                arr.set(i, arr.get(i - 1));
            }
            arr.set(i, placeThis);
        }
        long endMillisec = System.currentTimeMillis();
        return endMillisec - startMillisec;
    }

    /**
     * Merge Sort algorithm
     * 
     * @param arr   array of City objects to sort
     * @param comp  comparator to sort City objects
     * @return      time it took to sort in milliseconds
     */
    public long mergeSort(List<City> arr, Comparator<City> comp) {
        long startMillisec = System.currentTimeMillis();
        int n = arr.size();
        List<City> temp = new ArrayList<City>(n);
        for (int i = 0; i < n; i++) {
            temp.add(null);
        }
        recurse(arr, comp, temp, 0, n - 1);
        long endMillisec = System.currentTimeMillis();
        return endMillisec - startMillisec;
    }

    /**
     * Recurses to sort each part of the array using a conquer and
     * divide approach.
     * 
     * @param arr  array to sort
     * @param comp comparator to sort City objects
     * @param temp temporary array for values
     * @param from where to sort from
     * @param to   where to sort to
     */
    public void recurse(List<City> arr, Comparator<City> comp, List<City> temp, int from, int to) {
        if (to - from < 2) {
            if (to > from && comp.compare(arr.get(to), arr.get(from)) < 0)
                swap(arr, from, to);
        } else {
            int mid = (from + to) / 2;
            recurse(arr, comp, temp, from, mid);
            recurse(arr, comp, temp, mid + 1, to);
            merge(arr, comp, temp, from, mid, to);
        }
    }

    /**
     * Merges the array from "from" to "mid" and from "mid+1" to "to"
     * Merge is done on these two parts such that values are left in
     * ascending order.
     * 
     * @param arr  array to sort
     * @param temp temporary array for values
     * @param comp comparator to sort City objects
     * @param from start of the 1st part of the array
     * @param mid  end of the 1st part of the array and start of 2nd
     * @param to   end of the 2nd part of the array
     */
    public void merge(List<City> arr, Comparator<City> comp, List<City> temp, int from, int mid, int to) {
        int i = from, j = mid + 1, k = from;
        while (i <= mid & j <= to) {
            if (comp.compare(arr.get(i), arr.get(j)) < 0) {
                temp.set(k, arr.get(i));
                i++;
            } else {
                temp.set(k, arr.get(j));
                j++;
            }
            k++;
        }
        while (i <= mid) {
            temp.set(k, arr.get(i));
            i++;
            k++;
        }
        while (j <= to) {
            temp.set(k, arr.get(j));
            j++;
            k++;
        }
        for (k = from; k <= to; k++) {
            arr.set(k, temp.get(k));
        }
    }
}
