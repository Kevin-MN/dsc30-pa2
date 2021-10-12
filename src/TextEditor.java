/*
    Name: Kevin Morales-Nguyen
    PID:  A17186624
 */

/**
 * This is a class that utilizes the IntStack and StringStack objects
 * to create a pseudo text-editor,it also has various methods to perform
 * related operations such as insert,delete, undo and redo
 * @author Kevin Morales-Nguyen
 * @since  10/11/21
 */
public class TextEditor {
    private final static int STACK_SIZE = 10;
    private final static int CASE_CONVERT_OP = 0;
    private final static int INSERT_OP = 1;
    private final static int DELETE_OP = 2;

    /* instance variables */
    private String text;
    private IntStack undo;
    private StringStack deletedText;
    private IntStack redo;
    private StringStack insertedText;
    private int deletedTextRedoTracker;

    /**
     *This is the default constructor for the TextEditor class, it takes no arguments and
     * simply initializes the text attribute with an empty string and all stacks
     * with the default constructor.
     */
    public TextEditor() {
        this.text = "";
        this.deletedTextRedoTracker = 0;
        this.undo = new IntStack(STACK_SIZE);
        this.deletedText = new StringStack(STACK_SIZE);
        this.redo = new IntStack(STACK_SIZE);
        this.insertedText = new StringStack(STACK_SIZE);
    }

    /**
     * This method returns the text instance variable
     * @return String that represents the text attribute
     */
    public String getText() {
       return this.text;
    }

    /**
     * This method returns the length of the text attribute
     * @return int that represents the length of the String held in text attribute
     */
    public int length() {
       return this.text.length();
    }

    /**
     *This method updates the text attribute by converting the designated section of the
     * String, the i and j parameters specify the region to be case converted
     * @param i the starting index
     * @param j the ending index
     * @throws IllegalArgumentException if i or j is out of bound, or i is not smaller than j
     */
    public void caseConvert(int i, int j) {
        if(i < 0 || i > this.text.length()){
            throw new IllegalArgumentException("i is out of bounds");
        }

        if(j < 0 || j > this.text.length()){
            throw new IllegalArgumentException("j is out of bounds");
        }

        if(i >= j){
            throw new IllegalArgumentException("i is >= to j");
        }

        redo.clear();
        undo.push(i);
        undo.push(j);
        undo.push(CASE_CONVERT_OP);


        String first_half = this.text.substring(0,i);
        String target_convert = this.text.substring(i,j);
        String second_half = this.text.substring(j);

        char[] convert_chars = target_convert.toCharArray();
        String converted = "";

        for(int l = 0; l < convert_chars.length;l++){
            if(Character.isLowerCase(convert_chars[l])){
               converted = converted.concat(
                       String.valueOf(Character.toUpperCase(convert_chars[l])));
            }
            else{
                converted = converted.concat(
                        String.valueOf(Character.toLowerCase(convert_chars[l])));
            }
        }

        this.text = first_half.concat(converted.concat(second_half));

    }

    /**
     *This method will insert the specified string at the specified location denoted
     * by the input and i parameters respectively.
     * @param i the starting index
     * @param input the String to insert into the string
     * @throws NullPointerException if input is null
     * @throws IllegalArgumentException if i is out of bound
     */
    public void insert(int i, String input) {
        if(input == null){
            throw new NullPointerException("input is null");
        }

        if(i < 0 || i > this.text.length()){
            throw new IllegalArgumentException("i is out of bounds");
        }

        if(i == 0 && this.text.length() == 0){
            this.redo.clear();
            this.undo.push(i);
            this.undo.push(i + input.length());
            this.undo.push(INSERT_OP);

            this.text = input;
        }
        else{
            this.redo.clear();
            this.undo.push(i);
            this.undo.push(i + input.length());
            this.undo.push(INSERT_OP);

            String first_half = this.text.substring(0,i);
            String second_half = this.text.substring(i);
            this.text = first_half.concat(input.concat(second_half));
        }

    }

    /**
     * This methods will delete a section of the string contained by the text
     * instance variable, i and j denote the start and ending index of the section
     * to be removed
     * @param i starting index position
     * @param j ending index posisiton
     * @throws IllegalArgumentException if i or j is out of bound, or i is not smaller than j
     */
    public void delete(int i, int j) {
        if(i < 0 || i > this.text.length()){
            throw new IllegalArgumentException("i is out of bounds");
        }

        if(j < 0 || j > this.text.length()){
            throw new IllegalArgumentException("j is out of bounds");
        }

        if(i >= j){
            throw new IllegalArgumentException("i is >= to j");
        }

        this.redo.clear();
        this.undo.push(i);
        this.undo.push(j);
        this.undo.push(DELETE_OP);

        String first_half = this.text.substring(0,i);
        String delete = this.text.substring(i,j);
        String second_half = this.text.substring(j);

        this.deletedText.push(delete);

        this.text = first_half.concat(second_half);


    }

    /**
     * This method is used to implement a undo functionality with the text editor,
     * it utilizes the attribute stacks to keep track of what operations have
     * been done to the text String and can be called to undo said operations,
     * these operations are kept track of by using appropriate stacks which recored,
     * indeces, operation type and the actual string used in the operation
     * @return a boolean indicating whether the operation was succesful or not
     */
    public boolean undo() {
       if(this.undo.isEmpty()){
           return false;
       }
       else{
           int operation = undo.pop();
           int end_index = undo.pop();
           int start_index = undo.pop();

           switch(operation){
               case 0:
                   this.redo.push(start_index);
                   this.redo.push(end_index);
                   this.redo.push(operation);

                   String first_half = this.text.substring(0,start_index);
                   String target_convert = this.text.substring(start_index,end_index);
                   String second_half = this.text.substring(end_index);

                   char[] convert_chars = target_convert.toCharArray();
                   String converted = "";

                   for(int l = 0; l < convert_chars.length;l++){
                       if(Character.isLowerCase(convert_chars[l])){
                           converted = converted.concat(
                                   String.valueOf(Character.toUpperCase(convert_chars[l])));
                       }
                       else{
                           converted = converted.concat(
                                   String.valueOf(Character.toLowerCase(convert_chars[l])));
                       }
                   }

                   this.text = first_half.concat(converted.concat(second_half));

                   return true;
               case 1:
                   this.redo.push(start_index);
                   this.redo.push(end_index);
                   this.redo.push(operation);


                   String first_half1 = this.text.substring(0,start_index);
                   String delete = this.text.substring(start_index, end_index);
                   String second_half1 = this.text.substring(end_index);

                   this.insertedText.push(delete);

                   this.text = first_half1.concat(second_half1);

                   return true;
               case 2:

                   if(start_index == 0 && this.text.length() == 0){
                       this.redo.push(start_index);
                       this.redo.push(end_index);
                       this.redo.push(operation);

                       this.text = this.deletedText.pop();
                   }
                   else{
                       this.redo.push(start_index);
                       this.redo.push(end_index);
                       this.redo.push(operation);

                       String first_half2 = this.text.substring(0,start_index);
                       String second_half2= this.text.substring(start_index);
                       this.text = first_half2.concat(this.deletedText.pop().concat(second_half2));
                   }

                   return true;
               default:
                   return false;
           }

       }

    }

    /**
     * This operation implements a redo functionality by keeping track of what
     * operations have been undone and then allowing for them to be redone,
     * the undo operations are kept track of by using the redo stack and
     * coresponding int stack for indeces and operation
     * @return a boolean indicating whether the operation was succesful or not
     */
    public boolean redo() {
         if(this.redo.isEmpty()){
            return false;
        }
        else{
             int operation = redo.pop();
             int end_index = redo.pop();
             int start_index = redo.pop();

             switch(operation){
                 case 0:
                     this.undo.push(start_index);
                     this.undo.push(end_index);
                     this. undo.push(operation);

                     String first_half = this.text.substring(0,start_index);
                     String target_convert = this.text.substring(start_index,end_index);
                     String second_half = this.text.substring(end_index);

                     char[] convert_chars = target_convert.toCharArray();
                     String converted = "";

                     for(int l = 0; l < convert_chars.length;l++){
                         if(Character.isLowerCase(convert_chars[l])){
                             converted = converted.concat(
                                     String.valueOf(Character.toUpperCase(convert_chars[l])));
                         }
                         else{
                             converted = converted.concat(
                                     String.valueOf(Character.toLowerCase(convert_chars[l])));
                         }
                     }

                     this.text = first_half.concat(converted.concat(second_half));

                     return true;
                 case 1:

                     if(start_index == 0 && this.text.length() == 0){
                         this.undo.push(start_index);
                         this.undo.push(end_index);
                         this.undo.push(operation);

                         this.text = this.insertedText.pop();
                     }
                     else{
                         this.undo.push(start_index);
                         this.undo.push(end_index);
                         this.undo.push(operation);

                         String first_half1 = this.text.substring(0,start_index);
                         String second_half1= this.text.substring(start_index);
                         this.text = first_half1.concat(this.insertedText.pop().concat(second_half1));
                     }


                     return true;
                 case 2:
                     this.undo.push(start_index);
                     this.undo.push(end_index);
                     this.undo.push(operation);

                     String first_half2 = this.text.substring(0,start_index);
                     String delete = this.text.substring(start_index, end_index);
                     String second_half2 = this.text.substring(end_index);

                     this.deletedText.push(delete);

                     this.text = first_half2.concat(second_half2);

                     return true;
                 default:
                     return false;
             }

        }
    }

}


