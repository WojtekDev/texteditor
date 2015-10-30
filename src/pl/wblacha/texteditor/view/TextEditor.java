package pl.wblacha.texteditor.view;

import java.awt.Container;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.*;

import pl.wblacha.texteditor.view.helpers.MenuActionHelper;

public class TextEditor extends JFrame implements ActionListener {
    protected JTextArea mainTextArea;
    protected JScrollPane scrollPane;
    //the file submenu
    private JMenuItem fileNew, fileOpen, fileSave, fileSaveAs, fileSettings, fileExit;
    //the edit submenu
    private JMenuItem editCut, editCopy, editPaste, editClear, editRemove, editSelectAll, editFind;
    //the help menu
    private JMenuItem helpAbout;
    
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
        JMenu fileMenu, editMenu, helpMenu;
        
        //Create the menu bar
        menuBar = new JMenuBar();
        
        //Create the file menu
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        //Build the file submenu
        //File->New
        fileNew = createMenuItem("New", "alt+shift+n");
        fileMenu.add(fileNew);
        //  fileNew.addActionListener(this);
        //File->Open File...
        fileOpen = createMenuItem("Open File...", "");
        fileMenu.add(fileOpen);   
        //File->Save
        fileSave = createMenuItem("Save", "ctrl+s");
        fileMenu.add(fileSave);
        //File->Save as
        fileSaveAs = createMenuItem("Save as...", "");
        fileMenu.add(fileSaveAs); 
        //File->Settings
        fileSettings = createMenuItem("Settings", "");
        fileMenu.add(fileSettings);
        fileMenu.addSeparator();
        //File->Exit
        fileExit = createMenuItem("Exit", "");
        fileMenu.add(fileExit);        
        //Create the edit menu
        editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        //Build the edit submenu
        //Edit->Cut
        editCut = createMenuItem("Cut", "ctrl+x");
        editMenu.add(editCut); 
        //Edit->Copy
        editCopy = createMenuItem("Copy", "ctrl+c");
        editMenu.add(editCopy);         
        //Edit->Paste
        editPaste = createMenuItem("Paste", "ctrl+v");
        editMenu.add(editPaste);   
        //Edit->Clear text area
        editClear = createMenuItem("Clear text area", "");
        editMenu.add(editClear);          
        //Edit->Remove selected
        editRemove = createMenuItem("Remove selected", "");
        editMenu.add(editRemove);   
        //Edit->Select all
        editSelectAll = createMenuItem("Select all", "");
        editMenu.add(editSelectAll);
        editMenu.addSeparator();
        //Edit->Find
        editFind = createMenuItem("Find", "ctrl+f");
        editMenu.add(editFind);
        //Create the help menu
        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        //Build the help submenu
        //Edit->About text editor
        helpAbout = createMenuItem("About text editor", "");
        helpMenu.add(helpAbout);

        return menuBar;
    }
    
    /**
     * Creates shortcut keys for JMenuItem
     * 
     * @param menuItem
     * @param keyboardShortcut Shortcuts are usually triggered using Ctrl+KEY, sample: "ctrl+s"
     */
    public void setKeyboardShortcut(JMenuItem menuItem, String keyboardShortcut) {
        KeyStroke keyStroke = null;
        
        switch(keyboardShortcut.toLowerCase()){
            case "alt+shift+n":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.ALT_MASK + Event.SHIFT_MASK);
                break;
            case "ctrl+s":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK);
                break;
            case "ctrl+x":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK);
                break;
            case "ctrl+c":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK);
                break;
            case "ctrl+f":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK);
                break; 
        }
        
        if(keyStroke != null){
            menuItem.setAccelerator(keyStroke);
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
        if(e.getSource().equals(fileNew)) {
            MenuActionHelper.fileNew(mainTextArea);
        } else if(e.getSource().equals(fileOpen)) {
            try {
                MenuActionHelper.fileOpen(this, mainTextArea);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else {
            JMenuItem source = (JMenuItem)(e.getSource());
            String s = "Event source: " + source.getText();
            mainTextArea.append(s + "\n");   
        }
        
        
        
        
        
    }
}
