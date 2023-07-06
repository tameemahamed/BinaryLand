
package binaryland;

import java.awt.Color;
import javax.swing.JFrame;


public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame("Binary Land");
        frame.setBounds(500,200,570,480);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GamePanel panel = new GamePanel();
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);
        
        frame.setVisible(true);
    }
}
