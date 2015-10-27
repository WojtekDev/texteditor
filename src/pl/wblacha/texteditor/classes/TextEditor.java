package pl.wblacha.texteditor.classes;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TextEditor extends JFrame implements ActionListener {
        
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
        Container contentPane = getContentPane();
        contentPane.add(scrollPane, "Center");
        //Add the menu bar to the frame
        setJMenuBar(createMenuBar()); 
    }
    
    /**
     * Creates a menu bar with menu and sub-menus
     * 
     * @return JMenuBar
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar; 
        JMenu menu;
        
        //Create the menu bar
        menuBar = new JMenuBar();
        
        //Create the file menu
        menu = new JMenu("File");
        menuBar.add(menu);
        //Build the file submenu
        //File->New
        menu.add(createMenuItem("New", "alt+shift+n"));        
        //File->Open File...
        menu.add(createMenuItem("Open File...", ""));   
        //File->Save
        menu.add(createMenuItem("Save", "ctrl+s")); 
        //File->Save as
        menu.add(createMenuItem("Save as...", "")); 
        //File->Settings
        menu.add(createMenuItem("Settings", ""));
        menu.addSeparator();
        //File->Exit
        menu.add(createMenuItem("Exit", ""));        
        //Create the edit menu
        menu = new JMenu("Edit");
        menuBar.add(menu);
        //Build the edit submenu
        //Edit->Cut
        menu.add(createMenuItem("Cut", "ctrl+x")); 
        //Edit->Copy
        menu.add(createMenuItem("Copy", "ctrl+c"));         
        //Edit->Paste
        menu.add(createMenuItem("Paste", "ctrl+v"));   
        //Edit->Clear text area
        menu.add(createMenuItem("Clear text area", ""));          
        //Edit->Remove selected
        menu.add(createMenuItem("Remove selected", ""));   
        //Edit->Select all
        menu.add(createMenuItem("Select all", ""));
        menu.addSeparator();
        //Edit->Find
        menu.add(createMenuItem("Find", "ctrl+f"));
        //Create the help menu
        menu = new JMenu("Help");
        menuBar.add(menu);
        //Build the help submenu
        //Edit->About text editor
        menu.add(createMenuItem("About text editor", "")); 
   
        return menuBar;
    }
    
    /**
     * Creates shortcut keys for JMenuItem
     * 
     * @param menuItem
     * @param keyboardShortcut Shortcuts are usually triggered using Ctrl+KEY
     */
    public void setKeyboardShortcut(JMenuItem menuItem, String keyboardShortcut) {
        switch(keyboardShortcut.toLowerCase()){
            case "alt+shift+n":
                menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.ALT_MASK + java.awt.Event.SHIFT_MASK));
                break;
            case "ctrl+s":
                menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
                break;
            case "ctrl+x":
                menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));
                break;
            case "ctrl+c":
                menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK));
                break;
            case "ctrl+f":
                menuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.Event.CTRL_MASK));
                break; 
        }
    }
    
    
    /**
     * Returns a JMenuItem object
     * 
     * @param displayText the text of the JMenuItem
     * @param mnemonic the keyboard mnemonic for the JMenuItem
     * @return JMenuItem
     */
    public JMenuItem createMenuItem(String displayText, String keyboardShortcut) {
        JMenuItem menuItem = new JMenuItem(displayText);
        setKeyboardShortcut(menuItem, keyboardShortcut);
        menuItem.addActionListener(this);
        
        return menuItem;
    }
    
    @Override
    //This method is required by ActionListener
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Event source: " + source.getText();
        mainTextArea.append(s + "\n");    
    }
}
