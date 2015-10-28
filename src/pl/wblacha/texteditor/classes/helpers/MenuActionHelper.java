package pl.wblacha.texteditor.classes.helpers;

import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class MenuActionHelper {
    /**
     * Removes all the characters from the text area
     * 
     * @param textArea
     */
    public static void fileNew(JTextArea textArea) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure to create a new file?", "New", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if(choice == JOptionPane.YES_OPTION){
            textArea.setText("");
        }
    }
}
