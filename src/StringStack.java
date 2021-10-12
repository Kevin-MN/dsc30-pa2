/*
    Name: Kevin Morales-Nguyen
    PID:  A17186624
 */

import java.util.EmptyStackException;

/**
 * This class is the implementation of a stack that works with String objects, it is
 *  * implemented by using a String array that can be resized based on a expand/shrink factor,
 *  * there are also various methods to push a pop single or many elements and also check the
 *  * key attributes of the stack.
 * @author Kevin Morales-Nguyen
 * @since  10/11/21
 */
public class StringStack {
    private final static double DEFAULT_LOAD = 0.75;
    private final static double DEFAULT_SHRINK = 0.25;
    private final static double LOAD_FLOOR = 0.67;
    private final static double LOAD_CEIL = 1;
    private final static double SHRINK_FLOOR = 0;
    private final static double SHRINK_CEIL = 0.33;
    private final static int MINIMUM = 5;
    /* instance variables, feel free to add more if you need */
    private String[] data;
    private int nElems;
    private int init_capacity;
    private double loadFactor;
    private double shrinkFactor;

    /**
     *This is the constructor that contains all the paramaters for initialization
     * @param capacity a value that specifies the size of the internal array
     * @param loadF ratio that specifies when to double the array size, size/capacity
     * @param shrinkF ratio that specifies when to shrink the array buy half, size/capacity
     * @throws IllegalArgumentException if capacity, loadF or shrinkF is out of valid range
     */
    public StringStack(int capacity, double loadF, double shrinkF) {
        if(capacity < MINIMUM){
            throw new IllegalArgumentException("Capacity < 5, Invalid");
        }

        if(loadF < LOAD_FLOOR || loadF > LOAD_CEIL){
            throw new IllegalArgumentException("loadF < 0.67 || loadF > 1," +
                    " Load factor is outside of valid range");
        }

        if(shrinkF <= SHRINK_FLOOR || shrinkF > SHRINK_CEIL){
            throw new IllegalArgumentException("shrinkF <= 0 || shrinkFactor >0.33, " +
                    "Shrink factor is outside of valid range");
        }

        this.data = new String[capacity];
        this.nElems = 0;
        this.init_capacity = capacity;
        this.loadFactor = loadF;
        this.shrinkFactor  = shrinkF;
    }

    /**
     *This constructor takes the capacity and load factor arguments
     *
     * @param capacity a value that specifies the size of the internal array
     * @param loadF ratio that specifies when to double the array size, size/capacity
     * @throws IllegalArgumentException if capacity or loadF is out of valid range
     */
    public StringStack(int capacity, double loadF) {
        this(capacity, loadF, DEFAULT_SHRINK);
    }

    /**
     *This is the simplest constructor which only takes the capacity
     * @param capacity a value that specifies the size of the internal array
     * @throws IllegalArgumentException if capacity is out of valid range
     */
    public StringStack(int capacity) {
        this(capacity, DEFAULT_LOAD , DEFAULT_SHRINK);
    }


    /**
     *This methods returns a boolean indicating if the stack is "empty", nElems == 0
     * @return boolean, true if empty, false if not empty
     */
    public boolean isEmpty() {
        if(this.nElems == 0){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     *This method clears the stack by creating a new one using the initial capacity and
     * assigning it's reference to the data instance variable
     */
    public void clear() {
        this.data = new String[init_capacity];
        this.nElems = 0;
    }

    public int size() {
        return this.nElems;
    }

    /**
     * returns the total capcaity of the stack, which is the length of the internal array
     *
     * @return the size of the internal array, this.data.length
     */
    public int capacity() {
        return this.data.length;
    }

    /**
     *This method returns the top element of the stack, does not change it's state at all
     * @return top element on stack
     * @throws EmptyStackException if the stack is empty
     */
    public String peek() {
        if(nElems == 0){
            throw new EmptyStackException();
        }

        return this.data[nElems-1];
    }

    /**
     *This method pushes a value passed as an argument onto the stack, sets
     * value in internal array
     * @param element the value to push into the stack
     */
    public void push(String element) {
        double calc_loadF = ++nElems / (double)this.data.length;

        if(calc_loadF >= loadFactor){
            this.data = factor_size_copy(2.0);
            this.data[--nElems] = element;
        }
        else{
            this.data[--nElems] = element;
        }
        nElems++;
    }

    /**
     * This method returns the element on the top of the stack
     * @return top element in stack
     * @throws EmptyStackException if the stack is empty
     */
    public String pop() {
        if(nElems == 0){
            throw new EmptyStackException();
        }

        String top_element = this.data[nElems - 1];

        double calc_shrinkF = --nElems / (double)this.data.length;

        if(calc_shrinkF <= shrinkFactor ){
            this.data = factor_size_copy(.5);
        }

        return top_element;
    }


    /**
     *This method pushes multiple elements onto the stack that are in an array
     * @param elements an array containing elements to be pushed onto the stack
     * @throws IllegalArgumentException  if elements is null
     */
    public void multiPush(String[] elements) {
        if(elements == null){
            throw new IllegalArgumentException("elements is null");
        }

        for(int i = 0; i < elements.length;i++){
            this.push(elements[i]);
        }
    }

    /**
     * This methods pop multiple elements specified by the amount of elements as a parameter
     * @param amount number of elements to pop
     * @return array containing the elements that were popped in the order they came out
     * @throws IllegalArgumentException if
     */
    public String[] multiPop(int amount) {
        if(amount <= 0){
            throw new IllegalArgumentException("amount is non-positive number");
        }

        String[] return_arr = new String[amount];

        for(int i = 0;i < amount;i++){
            return_arr[i] = this.pop();
        }

        return return_arr;
    }


    /**
     * This is a private helper method that creates a new array that is
     * double or half the size, and copies the elements over to the new
     * array, it returns a reference to the newly resized array
     * @param factor the factor to multiple the existing size by, 2 or 1/2
     * @return a reference to the newly created array with new size and same elements
     */
    private String[] factor_size_copy(double factor){
        String[] copy_arr;
        if(factor * this.data.length < init_capacity){
            copy_arr = new String[init_capacity];
        }
        else{
            copy_arr = new String[(int)(this.data.length * factor)];
        }

        for(int i = 0; i <= this.nElems - 1; i++) {
            copy_arr[i] = this.data[i];
        }
        return copy_arr;
    }




}

