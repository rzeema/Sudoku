package sudoku;


import java.awt.*;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jack
 */
public class SquarePanel extends JPanel{
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        int s = (w < h ? w : h);
		
        return new Dimension(s, s);
    }
}
