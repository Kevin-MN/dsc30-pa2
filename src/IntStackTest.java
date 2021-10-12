import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class IntStackTest {
    IntStack empty_stack;
    IntStack non_empty_stack;
    int[] initialize = {1,2,3,4,5,6,7,8,9,10};

    @Before
    public void setup(){
        this.empty_stack = new IntStack(10);
        this.non_empty_stack = new IntStack(10);
        this.non_empty_stack.multiPush(initialize);
    }

    //FIRST CONSTRUCTOR TESTS

    @Test(expected = IllegalArgumentException.class)
    public void construct_1_test_1(){
        IntStack construct1 = new IntStack(5, -0.25, .75);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_1_test_2(){
        IntStack construct1 = new IntStack(2, .35, .75);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_1_test_3(){
        IntStack construct1 = new IntStack(5, .35, 1);
    }


    //SECOND CONSTRUCTOR TESTS

    @Test(expected = IllegalArgumentException.class)
    public void construct_2_test_1(){
        IntStack construct2 = new IntStack(5, -0.25);
    }

    @Test(expected = IllegalArgumentException.class)
    public void construct_2_test_2(){
        IntStack construct2 = new IntStack(3, .75);
    }

    @Test
    public void construct_2_test_3(){
        IntStack construct2 = new IntStack(5, .75);
        assertNotNull(construct2);
    }

    //THIRD CONSTRUCTOR TESTS

    @Test(expected = IllegalArgumentException.class)
    public void construct_3_test_1(){
        IntStack construct3 = new IntStack(3);
    }

    @Test
    public void construct_3_test_2(){
        IntStack construct3 = new IntStack(5);
       assertEquals(0, construct3.size());
    }

    @Test
    public void construct_3_test_3(){
        IntStack construct3 = new IntStack(10);
        assertEquals(10, construct3.capacity());
    }


    // isEmpty() TESTS

    @Test
    public void isEmptyTest1() {
       assertEquals(true, this.empty_stack.isEmpty());

    }

    @Test
    public void isEmptyTest2() {
        assertEquals(false, this.non_empty_stack.isEmpty());
    }

    @Test
    public void isEmptyTest3() {
        assertNotEquals(true, this.non_empty_stack.isEmpty());
    }

    //clear() TESTS

    @Test
    public void clearTest1() {
        this.empty_stack.clear();
        assertEquals(true, this.empty_stack.isEmpty());
    }

    @Test
    public void clearTest2() {
        this.non_empty_stack.clear();
        assertEquals(true, this.non_empty_stack.isEmpty());
    }

    @Test
    public void clearTest3() {
        this.non_empty_stack.clear();
       assertEquals(0, this.non_empty_stack.size());
    }

    // size() TESTS

    @Test
    public void sizeTest1() {
        assertEquals(0, this.empty_stack.size());
    }

    @Test
    public void sizeTest2() {
        assertEquals(initialize.length, this.non_empty_stack.size());
    }

    @Test
    public void sizeTest3() {
        assertNotEquals(0, this.non_empty_stack.size());
    }

    // capacity() TEST

    @Test
    public void capacityTest1() {
        assertEquals(10, this.empty_stack.capacity());
    }

    @Test
    public void capacityTest2() {
        assertEquals(20, this.non_empty_stack.capacity());
    }

    @Test
    public void capacityTest3() {
        non_empty_stack.multiPush(new int[8]);
        assertEquals(40, this.non_empty_stack.capacity());
    }

    // peek() TESTS

    @Test(expected = EmptyStackException.class)
    public void peekTest1() {
       this.empty_stack.peek();
    }

    @Test
    public void peekTest2(){ assertEquals(10, this.non_empty_stack.peek());
    }

    @Test
    public void peekTest3() {
        empty_stack.push(1);
        assertEquals(1, this.empty_stack.peek());
    }

    // push TESTS

    @Test
    public void pushTest1() {
        this.empty_stack.push(10);
       assertEquals(10, this.empty_stack.peek());
    }

    @Test
    public void pushTest2() {
        this.non_empty_stack.push(10);
        assertEquals(10, this.non_empty_stack.peek());
    }

    @Test
    public void pushTest3() { // test is load factor works properly, push should double size
        for(int i = 0; i < 8;i++){
            this.empty_stack.push(i);
        }

        assertEquals(20, this.empty_stack.capacity());
    }

    // pop TESTS

    @Test(expected = EmptyStackException.class)
    public void popTest1() {
        this.empty_stack.pop();
    }

    @Test
    public void popTest2() {
        assertEquals(10, this.non_empty_stack.pop());
    }

    @Test
    public void popTest3() {
        this.empty_stack.push(-10);
        assertEquals(-10, this.empty_stack.pop());
    }

    // multiPush() TESTS

    @Test(expected = IllegalArgumentException.class)
    public void multiPushTest1() {
        this.empty_stack.multiPush(null);
    }

    @Test
    public void multiPushTest2() {
        int[] mult_push = new int[8];
        this.empty_stack.multiPush(mult_push);
        assertEquals(20, this.empty_stack.capacity());
    }

    @Test
    public void multiPushTest3() {
        int[] mult_push = new int[20];
        this.non_empty_stack.multiPush(mult_push);
        assertEquals(30, this.non_empty_stack.size());
    }

    //multiPop() TESTS

    @Test(expected = IllegalArgumentException.class)
    public void multiPopTest1() {
        this.empty_stack.multiPop(-5);
    }

    @Test(expected = EmptyStackException.class)
    public void multiPopTest4() {
        this.empty_stack.multiPop(10);
    }

    @Test
    public void multiPopTest2() {
        this.non_empty_stack.multiPop(5);
        assertEquals(5, this.non_empty_stack.size());
    }


    @Test
    public void multiPopTest3() {
        int[] expected = {10,9,8,7,6};
        assertArrayEquals(expected, this.non_empty_stack.multiPop(5) );
    }







}