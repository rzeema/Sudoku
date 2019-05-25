/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class sudoku extends JFrame implements ActionListener, KeyListener{
    
    private int GRID_SIZE = 9;    // Size of the board
    
    private Color lBlue = new Color(233,239,248);
    private Color userC = new Color(119,119,221);
    
    private Font boardF = new Font("serif", Font.BOLD, 30);
    private Font userF = new Font("Monospaced", Font.BOLD, 30);
    
    private SquarePanel SquareSudoku;
    private JPanel sudokuPanel;
    private JPanel buttonPanel;
    private JButton load,solve,clear,check,info,site;
    private JToggleButton hint ;
    
    private boolean hintSwitch ;
    private ImageIcon helpIcon;
    private ImageIcon siteIcon;
    private JTextField title;
    
    private ActionListener toggleListener;
    private JTextField[][] tFields = new JTextField[GRID_SIZE][GRID_SIZE];

    private int[][] puzzle =
        {{2, 0, 0, 1, 0, 0, 3, 0, 0},
        {1, 0, 0, 0, 0, 4, 0, 7, 0},
        {0, 0, 0, 0, 6, 0, 0, 0, 9},
        {9, 0, 7, 0, 3, 0, 0, 2, 0},
        {0, 2, 6, 0, 7, 0, 0, 0, 1},
        {4, 0, 0, 0, 1, 0, 0, 9, 0},
        {0, 3, 0, 9, 0, 7, 0, 8, 0},
        {0, 0, 4, 0, 0, 0, 0, 0, 6},
        {8, 9, 0, 0, 0, 0, 0, 0, 0}};
    
    public sudoku(){
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        //sudokuPanel
        sudokuPanel = new JPanel();
        sudokuPanel.setLayout(new GridLayout(9,9));
        
        //SquareSudoku
        SquareSudoku = new SquarePanel();
        SquareSudoku.add(sudokuPanel);
        cp.add(SquareSudoku, BorderLayout.CENTER);
        
        //buttonPanel
        buttonPanel = new JPanel();
        
        siteIcon = new ImageIcon("C:/Users/Jack/Desktop/ICS4U/Culminating/site.png");
        siteIcon = new ImageIcon(siteIcon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        site = new JButton(siteIcon);
        site.setPreferredSize(new Dimension(30, 30));
        site.addActionListener(this);
        site.setToolTipText("Website to generate Sudokus");
        buttonPanel.add(site);
        
        load = new JButton("Load");
        load.setPreferredSize(new Dimension(70, 30));
        load.addActionListener(this);
        load.setToolTipText("Load a puzzle generated from https://qqwing.com/generate.html");
        buttonPanel.add(load);
        
        solve = new JButton("Solve");
        solve.setPreferredSize(new Dimension(70, 30));
        solve.addActionListener(this);
        solve.setToolTipText("Solves entire sudoku if puzzle is solvable");
        buttonPanel.add(solve);
        
        clear = new JButton("Clear");
        clear.setPreferredSize(new Dimension(70, 30));
        clear.addActionListener(this);
        clear.setToolTipText("Reset the game");
        buttonPanel.add(clear);
        
        check = new JButton("Check");
        check.setPreferredSize(new Dimension(70, 30));
        check.addActionListener(this);
        check.setToolTipText("Checks if user is correct");
        buttonPanel.add(check);
        
        hint = new JToggleButton("Hints Mode",false);
        hint.setPreferredSize(new Dimension(100, 30));
        hint.setToolTipText("Turn ON/OFF hint mode");

        toggleListener= new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JToggleButton btn = (JToggleButton) e.getSource();
                if(btn.isSelected()){
                    btn.setText("Hints: ON");
                    hintSwitch = true;
                    
                    for (int row = 0; row < 9; row++){
                        for (int col = 0; col < 9; col++){
                            String fieldString = tFields[row][col].getText();
                            if (tFields[row][col].isEditable() && !"".equals(fieldString)){
                                int temp = puzzle[row][col];
                                puzzle[row][col] = 0;
                                if(isValid(row,col,temp)){
                                    tFields[row][col].setForeground(Color.GREEN);
                                }
                                else{
                                    tFields[row][col].setForeground(Color.RED);
                                }
                                puzzle[row][col] = temp;
                            }
                        }
                    }
                }
                else{
                    btn.setText("Hints: OFF");
                    hintSwitch = false;
                    for (int row = 0; row < 9; row++){
                        for (int col = 0; col < 9; col++){
                            if (tFields[row][col].isEditable()){
                                tFields[row][col].setForeground(userC);
                            }
                        }
                    }
                }
            }
        };
        hint.addActionListener(toggleListener);
        buttonPanel.add(hint);
        
        helpIcon = new ImageIcon("C:/Users/Jack/Desktop/ICS4U/Culminating/help.png");
        helpIcon = new ImageIcon(helpIcon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        info = new JButton(helpIcon);
        info.setPreferredSize(new Dimension(30, 30));
        info.addActionListener(this);
        info.setToolTipText("Program Instructions");
        buttonPanel.add(info);
        
        cp.add(buttonPanel, BorderLayout.SOUTH);
        
        title = new JTextField("Sudoku");
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Monospaced", Font.BOLD, 50));
        title.setEnabled(false);
        title.setHorizontalAlignment(JTextField.CENTER);
        
        cp.add(title, BorderLayout.NORTH);
        
        //end of gui

        loadPuzzle();
        hintSwitch = false;
        
        sudokuPanel.setPreferredSize(new Dimension(9*60,9*60));
        cp.setPreferredSize(new Dimension(9*60+13,9*60+115));
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        
        JButton btn = (JButton) e.getSource();
        
        if (btn.getIcon()==siteIcon){
            openWeb("https://qqwing.com/generate.html");
        }
        if (btn.getText()=="Load"){
            String userPuzzle = JOptionPane.showInputDialog(null,"Please paste your puzzle code:","Load Puzzle",JOptionPane.QUESTION_MESSAGE);
            
            try{
                loadPuzzle(getPuzzle(userPuzzle));
                revalidate();
            }
            catch(Exception ex){
                System.out.println("Exception "+ex.getMessage());
            }
        }
        if (btn.getText()=="Solve"){
            clear();
            solve(0,0);
        }
        if (btn.getText()=="Clear"){
            clear();
        }
        if (btn.getText()=="Check"){
            if(validateCheck()){
                JOptionPane.showMessageDialog(null, "All numbers are valid. You win!", "Puzzle Checker", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("You win");
            }
            else{
                System.out.println("Incorrect");
                JOptionPane.showMessageDialog(null, "Not all numbers are valid. Try again.", "Puzzle Checker", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (btn.getIcon()==helpIcon){
            JOptionPane.showMessageDialog(null, "Basic Rules:\n" +
            "\n" +
            "Each puzzle consists of a 9x9 grid containing given clues in various places.              \n" +
            "The object is to fill all empty squares so that the numbers 1 to 9 appear \n" +
            "exactly once in each row, column and 3x3 box.\n" +
            "\n" +
            "Load Button:\n" +
            "\n" +
            "Users can generate a puzzle from: https://qqwing.com/generate.html and load\n" +
            "it. (Output format must be \"one Line\")\n" +
            "\n" +
            "Solve:\n" +
            "\n" +
            "Completes the current Sudoku recursively. The solution will not be incorrect\n" +
            "if the puzzle incorrect.\n" +
            "\n" +
            "Clear:\n" +
            "\n" +
            "Resets the current puzzle.\n" +
            "\n" +
            "Check:\n" +
            "\n" +
            "Checks if the user has won and return the result.\n" +
            "\n" +
            "Hints Mode Toggle:\n" +
            "\n" +
            "While ON the user inputs will be colored to indicate validity,\n" +
            "RED = invalid      GREEN = valid ", "Sudoku Instructions", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void keyTyped(KeyEvent e) {
        //not used
    }
    
    public void keyPressed(KeyEvent e) {
        
        //Resets any input other than integers 1 to 9 to ""
        //Resets a cell value to 0 when backspace is pressed (must reset or users can delete cells but have wrong hints because puzzle isn't updated)
        
        JTextField num = (JTextField) e.getSource();
        num.setText("");
        int r=0,c=0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if(tFields[row][col]==num){
                    r=row;
                    c=col;
                }            
            }
        }
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_BACK_SPACE) {
            puzzle[r][c]=0;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        JTextField num = (JTextField) e.getSource();
        int input;
        try{
            input = Integer.parseInt(num.getText());
            if(input==0||input>9||input<0){
                num.setText("");
            }
        }
        catch(Exception ex){
            num.setText("");
            ex.printStackTrace();
            return;
        }
        int r=0,c=0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if(tFields[row][col]==num){
                    r=row;
                    c=col;
                }            
            }
        }
        
        // if hints are on checks whether the user input is valid or not
        
        if (hintSwitch){
            if(isValid(r,c,input)){
                tFields[r][c].setForeground(Color.GREEN);
            }
            else{
                tFields[r][c].setForeground(Color.RED);
            }
        }
        puzzle[r][c]= input;
    }
    
    //checks which block is to be printed white
    public boolean isWhite(int row, int col){
        int row3x3 =(row/3);
        int col3x3 =(col/3);
        
        return (row3x3==0 && col3x3==1)||(row3x3==1 && col3x3==0)||(row3x3==1 && col3x3==2)||(row3x3==2 && col3x3==1);
    }
    
    // Puzzle code to Array method
    //reads a string and puts the integers into a 9x9 2d array periods(dot) represents 0s
    
    public static int[][] getPuzzle(String puzzleData){
        int[][] newPuzzle = new int[9][9];
        for(int row = 0;row < 9;row++){
            for(int col = 0;col < 9;col++){
                if (puzzleData.charAt(row*9+col)=='.'){
                    newPuzzle[row][col]=0;
                }
                else{
                    newPuzzle[row][col]=Character.getNumericValue(puzzleData.charAt(row*9+col));
                }
            }
        }
        return newPuzzle;
    }
    
    //Load Puzzle Method
    //loads the default puzzle 
    
    public void loadPuzzle(){
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                tFields[row][col] = new JTextField();
                sudokuPanel.add(tFields[row][col]);          

                if(puzzle[row][col]==0){
                    tFields[row][col].setText("");
                    tFields[row][col].addKeyListener(this);
                    tFields[row][col].setFont(userF);
                    tFields[row][col].setForeground(userC);
                    tFields[row][col].setEditable(true);
                }

                else{
                    tFields[row][col].setText(puzzle[row][col] + "");
                    tFields[row][col].setFont(boardF);
                    tFields[row][col].setEditable(false);
                }
                
                if(isWhite(row,col)){
                    tFields[row][col].setBackground(Color.WHITE);
                }
                
                else{
                    tFields[row][col].setBackground(lBlue);
                }
                
                tFields[row][col].setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }
    
    // Load Puzzle Method
    //takes a 2d int array and assign the values to a text fielf array
    //zeroes become empty text fiels that are user editable
    //numbers become uneditable 
    
    public void loadPuzzle(int[][] puzzleIn){
        puzzle = puzzleIn;
        sudokuPanel.removeAll();
        
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                tFields[row][col] = new JTextField();
                sudokuPanel.add(tFields[row][col]);

                if(puzzle[row][col]==0){
                    tFields[row][col].setText("");
                    tFields[row][col].setEditable(true);
                    tFields[row][col].addKeyListener(this);
                    tFields[row][col].setFont(userF);
                    tFields[row][col].setForeground(userC);
                }

                else{
                    tFields[row][col].setText(puzzle[row][col] + "");
                    tFields[row][col].setEditable(false);
                    tFields[row][col].setFont(boardF);
                    tFields[row][col].setForeground(Color.BLACK);
                }
                
                if(isWhite(row,col)){
                    tFields[row][col].setBackground(Color.WHITE);
                }
                
                else{
                    tFields[row][col].setBackground(lBlue);
                }
                
                tFields[row][col].setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }
    
    //checks if there are numbers 1 through 9 within a row and collumn
    //checks if there are numbers 1 through 9 within a subgrid
    
    public boolean isValid(int row, int col,int val){
        
        for(int i=0;i<9;i++){
            if (val==puzzle[row][i]){
                return false;
            }
        }
        
        for(int i=0;i<9;i++){
            if (val==puzzle[i][col]){
                return false;
            }
        }
        
        //subgrid
        
        int row3x3 =(row/3)*3;
        int col3x3 =(col/3)*3;
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(val==puzzle[row3x3+i][col3x3+j]){
                    return false;
                }
            }
        }
        return true;
    }
    
    //starts at specified cell and moves from user editable cell to cell 
    //recursively assigns a valid value to a cell and moves on until there are no valid numbers to assign then backtracks to last cell and try the next valid value until solved
    
    public boolean solve(int row,int col){
        if (col==9){
            col=0;
            row++;
            if(row==9){
                return true;
            }
        }
        
        if (puzzle[row][col] != 0) {
            return solve(row,col+1);
        }
        
        for (int i = 1; i <= 9; i++){
            if(isValid(row,col,i)){
                puzzle[row][col]=i;
                tFields[row][col].setText(i + "");
                if(solve(row,col+1)){
                    return true;
                }
            }
        }
        puzzle[row][col]=0;
        return false;
    }
    
    //Checks if all user editable cells are correct
    
    public boolean validateCheck(){
        for (int row = 0; row < 9; row++){
            for (int col = 0; col < 9; col++){
                if (tFields[row][col].isEditable()){
                    int temp = puzzle[row][col];
                    puzzle[row][col] = 0;
                    int numValue;
                    try{
                        numValue = Integer.parseInt(tFields[row][col].getText());                        
                    }
                    catch(NumberFormatException ex){
                        return false;
                    }
                    
                    if (!isValid(row,col,numValue)){
                        return false;
                    }
                    puzzle[row][col] = temp;
                }
            }
            
        }
        return true;
    }
    
    //resets all user editable cells
    
    public void clear(){
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if ( tFields[row][col].isEditable()){
                    tFields[row][col].setText("");
                    tFields[row][col].setForeground(userC);
                    puzzle[row][col]=0;
                }
            }
        }
    }

    //opens a string containing an url
    
    public static void openWeb(String site) {
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(site));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new sudoku();        
    }
}