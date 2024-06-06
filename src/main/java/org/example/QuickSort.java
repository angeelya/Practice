package org.example;

import java.util.Comparator;

/**
 * @author Angelina
 *
 * The `QuickSort` class provides methods for sorting an `ArrayCollection`
 * using the QuickSort algorithm. It supports both natural ordering (using
 * `Comparable`) and custom ordering (using a `Comparator`).
 */
public class QuickSort {

    /**
     * Sorts the given `ArrayCollection` in ascending order using the natural
     * ordering of its elements (if they implement `Comparable`).
     *
     * @param list the collection to be sorted
     * @param <T>  the type of elements in the collection
     */
     public static  <T extends Comparable<T>> void sort(ArrayCollection<T> list) {
        quickSort(list,0, list.length()-1);
    }

    /**
     * Sorts the given `ArrayCollection` using a custom ordering specified by
     * the provided `Comparator`.
     *
     * @param list       the collection to be sorted
     * @param comparator the custom comparator to define the ordering
     * @param <T>        the type of elements in the collection
     */
    public static <T> void sort(ArrayCollection<T> list, Comparator<T> comparator)
    {
        quickSort(list,0,list.length()-1,comparator);
    }

    /**
     * Performs the QuickSort algorithm on the specified range of the collection.
     *
     * @param list       the collection to be sorted
     * @param begin      the starting index of the range
     * @param end        the ending index of the range
     * @param comparator the custom Comparator
     * @param <T>        the type of elements in the collection
     */
    private static <T> void quickSort(ArrayCollection<T> list, int begin, int end,Comparator<T> comparator) {
        if(begin<end)
        {
            int baseIndex= partition(list,begin,end,comparator);
            quickSort(list,begin,baseIndex-1,comparator);
            quickSort(list,baseIndex+1,end,comparator);
        }
    }
    /**
     * Partitions the collection into two sub arrays based on a middle pivot element.
     *
     * @param list       the collection to be partitioned
     * @param begin      the starting index of the range
     * @param end        the ending index of the range
     * @param comparator the custom Comparator
     * @param <T>        the type of elements in the collection
     * @return the new index of the pivot element after partitioning
     */
    private static <T> int partition(ArrayCollection<T> list, int begin, int end, Comparator<T> comparator) {
        int middle = begin + (end - begin) / 2;
        T baseObject=list.get(middle);
        swap(list,middle,end);
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (comparator.compare(list.get(j),baseObject) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, end);
        return i + 1;
    }

    /**
     * Performs the QuickSort algorithm on the specified range of the collection
     * using natural ordering (if elements implement `Comparable`).
     *
     * @param list  the collection to be sorted
     * @param begin the starting index of the range
     * @param end   the ending index of the range
     * @param <T>   the type of elements in the collection
     */
    private static <T extends Comparable<T>> void quickSort(ArrayCollection<T > list, int begin, int end) {
        if(begin<end)
        {
            int baseIndex= partition(list,begin,end);
            quickSort(list,begin,baseIndex-1);
            quickSort(list,baseIndex+1,end);
        }
    }

    /**
     * Partitions the collection into two sub arrays based on a last pivot element
     * using natural ordering (if elements implement `Comparable`).
     *
     * @param list  the collection to be partitioned
     * @param begin the starting index of the range
     * @param end   the ending index of the range
     * @param <T>   the type of elements in the collection
     * @return the new index of the pivot element after partitioning
     */
    private static <T extends Comparable<T>> int partition(ArrayCollection<T> list, int begin, int end) {
        T baseObject = list.get(end);
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (list.get(j).compareTo(baseObject) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, end);
        return i + 1;
    }

    /**
     * Swaps two elements in the collection.
     *
     * @param list the collection containing the elements
     * @param i    the index of the first element
     * @param j    the index of the second element
     * @param <T>  the type of elements in the collection
     */
    private static <T> void swap(ArrayCollection<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
