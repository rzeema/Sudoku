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
/**
 *
 * @author Jack
 */
public class FRAMESTUFF extends JFrame{
    private JPanel sudokuPanel;
    private JPanel buttonPanel;
    private JButton load,solve,clear;
    
    public FRAMESTUFF(){
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        //sudokuPanel
        sudokuPanel = new JPanel();
        sudokuPanel.setLayout(new GridLayout(9,9));
        cp.add(sudokuPanel, BorderLayout.NORTH);
        
        //buttonPanel
        buttonPanel = new JPanel();
        
        load = new JButton("Load");
        load.setPreferredSize(new Dimension(80, 40));
        buttonPanel.add(load);
        
        solve = new JButton("Solve");
        solve.setPreferredSize(new Dimension(80, 40));
        buttonPanel.add(solve);
        
        clear = new JButton("Clear");
        clear.setPreferredSize(new Dimension(80, 40));
        buttonPanel.add(clear);
        
        cp.add(buttonPanel, BorderLayout.SOUTH);
        
        
        cp.setPreferredSize(new Dimension(9*60,9*60));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new FRAMESTUFF();
    }

}
