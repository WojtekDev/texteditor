package pl.wblacha.texteditor.classes;

import java.awt.Container;

import javax.swing.*;

public class TextEditor extends JFrame {
        
    protected JTextArea mainTextArea;
    protected JScrollPane scrollPane;
    
    public TextEditor() {
        setTitle("Text editor - developed by Wojciech Blacha 2015");
        setSize(600, 600);
        //Create an editable text area
        mainTextArea = new JTextArea(8, 20);
        mainTextArea.setLineWrap(true);
        mainTextArea.setEditable(true);
        //Add a scroll bar to the text area
        scrollPane = new JScrollPane(mainTextArea);
        //Add the scroll pane into the content pane
        Container textEditorContent = getContentPane();
        textEditorContent.add(scrollPane, "Center");
    }
}
