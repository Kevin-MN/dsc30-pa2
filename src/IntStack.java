/*
    Name: Kevin Morales-Nguyen
    PID:  A17186624
 */

import java.util.EmptyStackException;

/**
 * This class is the implementation of a stack that works with primitive integers, it is
 * implemented by using a integer array that can be resized based on a expand/shrink factor,
 * there are also various methods to push a pop single or many elements.
 * @author Kevin Morales Nguyen
 * @since  10/6/2021
 */
public class IntStack {

    /* instance variables, feel free to add more if you need */
    private int[] data;
    private int nElems;
    private int init_capacity;
    private double loadFactor;
    private double shrinkFactor;

    public IntStack(int capacity, double loadF, double shrinkF) {
        if(capacity < 5){
            throw new IllegalArgumentException("Capacity <= 0, Invalid");
        }

        if(loadF < 0.67 || loadF > 1){
            throw new IllegalArgumentException("Load factor is outside of valid range");
        }

        if(shrinkF <= 0 || shrinkFactor >0.33){
            throw new IllegalArgumentException("Shrink factor is outside of valid range");
        }

        this.data = new int[capacity];
        this.nElems = 0;
        this.init_capacity = capacity;
        this.loadFactor = loadF;
        this.shrinkFactor  = shrinkF;

    }

    public IntStack(int capacity, double loadF) {
        this(capacity, loadF, 0.25);
    }

    public IntStack(int capacity) {
        this(capacity, 0.75 , 0.25);
    }

    public boolean isEmpty() {
        if(this.nElems == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void clear() {
        this.data = new int[init_capacity];
        this.nElems = 0;
    }

    public int size() {
        return this.nElems;

    }

    public int capacity() {
        return this.data.length;
    }

    public int peek() {
        return this.data[nElems-1];
    }

    public void push(int element) {

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

    public int pop() {
        nElems--;
        int temp = this.data[nElems - 1];

        //double shrink_factor =
        //if(){

        //}




        return temp;
    }

    public void multiPush(int[] elements) {
        nElems+= elements.length;

    }

    public int[] multiPop(int amount) {
        /* TODO */
        return null;
    }


    private int[] factor_size_copy(double factor){

        int[] temp = new int[(int)(this.data.length * factor)];
        for(int i = 0; i < this.nElems - 1; i++) {
            temp[i] = this.data[i];
        }
        return temp;
    }

    public void print_arr_using_size(){
        for(int i = 0;i< nElems;i++){
            System.out.print(this.data[i] + " ");
        }
        System.out.println();
    }

    public void print_arr(){
        for(int i = 0;i< this.data.length;i++){
            System.out.print(this.data[i] + " ");
        }
        System.out.println();
    }
}