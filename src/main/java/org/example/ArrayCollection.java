package org.example;

import java.util.Arrays;
import java.util.NoSuchElementException;
/**
 * @author Angelina
 *
 * A generic collection class that provides dynamic resizing and various operations.
 *
 * @param <T> the generic type of elements stored in the collection
 */
public class ArrayCollection<T> {
    private T[] array, extraArray;
    private int actualSize = 0;

    /**
     * Constructs an ArrayCollection with an initial capacity of 10.
     */
    public ArrayCollection() {
        array = (T[]) new Object[10];
    }

    /**
     * Constructs an ArrayCollection with a specified initial capacity.
     *
     * @param capacity the initial capacity of the collection
     */

    public ArrayCollection(int capacity) {
        array = (T[]) new Object[capacity];
    }

    /**
     * Adds an element to the collection.
     *
     * @param object the element with the generic type to add
     */

    public void add(T object) {
        if (actualSize == array.length) {
            extend();
        }
        array[actualSize] = object;
        actualSize++;
    }

    /**
     * Adds an element at a specific index in the collection.
     *
     * @param object the element with the generic type to add
     * @param index  the index at which to add the element
     */

    public void add(T object, int index) {
        if (index > actualSize || index < -1)
            throw new ArrayIndexOutOfBoundsException("Exit of out the collection range bounds");
        else if (actualSize == array.length) {
            extend();
        }
        extraArray = (T[]) new Object[actualSize - index];
        arrayCopy(array, index, extraArray, 0, extraArray.length);
        array[index] = object;
        arrayCopy(extraArray, 0, array, index + 1, extraArray.length);
        actualSize++;
    }

    /**
     * Adds all elements from another ArrayCollection to this collection.
     *
     * @param objects the collection of elements to add
     */

    public void add(ArrayCollection<T> objects) {
        int size = actualSize;
        actualSize += objects.length();
        if (actualSize > array.length) {
            extend();
        }
        arrayCopy(objects.toArray(), 0, array, size, objects.length());
    }

    /**
     * Sets the value of an element at a specific index in the collection.
     *
     * @param index  the index of the element to set
     * @param object the new value for the element
     */
    public void set(int index, T object) {
        if (index > actualSize || index < -1)
            throw new ArrayIndexOutOfBoundsException("Exit of out the collection range bounds");
        array[index] = object;
    }

    /**
     * Returns the number of elements in the collection.
     *
     * @return the size of the collection
     */
    public Integer length() {
        return actualSize;
    }

    /**
     * Removes an element at a specific index from the collection.
     *
     * @param index the index of the element to remove
     */
    public void remove(int index) {
        if (index > actualSize || index < -1)
            throw new ArrayIndexOutOfBoundsException("Exit of out the collection range bounds");
        if (index > -1) {
            if (actualSize - 1 - index >= 0)
                arrayCopy(array, index + 1, array, index, actualSize - 1 - index);
            array[actualSize - 1] = null;
            actualSize--;
        }
    }

    /**
     * Removes the first occurrence of a specified element from the collection.
     *
     * @param object the element to remove
     * @throws NoSuchElementException if the element is not found in the collection
     */
    public void remove(T object) {
        boolean isFound = false;
        for (int i = 0; i < actualSize; i++) {
            if (array[i].equals(object)) {
                isFound = true;
                remove(i);
                break;
            }
        }
        if (!isFound) throw new NoSuchElementException("Element of collection not found");
    }

    /**
     * Retrieves an element from the collection at a specific index.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     */
    public T get(int index) {
        if (index > actualSize || index < -1)
            throw new ArrayIndexOutOfBoundsException("Exit of out the collection range bounds");
        return array[index];
    }

    /**
     * Checks whether the collection contains a specified element.
     *
     * @param object the element to search for
     * @return true if the element is found, false otherwise
     */
    public boolean contains(T object) {
        for (int i = 0; i < actualSize; i++) {
            if (array[i].equals(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an array containing all elements in the collection.
     *
     * @return an array representation of the collection
     */
    public T[] toArray() {
        trimArray();
        return extraArray;
    }

    /**
     * Returns the contents of a collection as a string containing all the elements of the collection.
     *
     * @return representation of the collection as a string
     */

    @Override
    public String toString() {
        trimArray();
        return Arrays.toString(extraArray);
    }

    /**
     * Clears the collection by resetting its size to zero.
     */
    public void clear() {
        array = (T[]) new Object[array.length];
        actualSize = 0;
    }

    /**
     * Extends the internal array when needed.
     */
    private void extend() {
        extraArray = (T[]) new Object[3 * actualSize / 2 + 1];
        arrayCopy(array, 0, extraArray, 0, actualSize);
        array = extraArray;
    }

    /**
     * Trims the internal array to the actual size of the collection.
     */
    private void trimArray() {
        extraArray = (T[]) new Object[actualSize];
        arrayCopy(array, 0, extraArray, 0, actualSize);
    }

    /**
     * Copies elements from the source array to the destination array.
     *
     * @param source          the source array
     * @param sourceStartIndex the starting index in the source array
     * @param destination     the destination array
     * @param destStartIndex  the starting index in the destination array
     * @param size            the number of elements to copy
     */
    private void arrayCopy(T[] source, int sourceStartIndex,
                           T[] destination, int destStartIndex, int size) {
        for (int i = 0; i < size; i++) {
            destination[destStartIndex + i] = source[sourceStartIndex + i];
        }
    }
}
