package pl.wblacha.texteditor.components;

import java.awt.Container;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

import pl.wblacha.texteditor.components.helpers.MenuActionHelper;

public class TextEditor extends JFrame implements ActionListener {
    public static String TEXT_EDITOR_TITLE = "Text editor - developed by Wojciech Blacha 2015";
    
    protected JTextArea mainTextArea;
    protected JScrollPane scrollPane;
    protected String currentFilePath = "";
    
    //the file submenu
    private JMenuItem fileNew, fileOpen, fileSave, fileSaveAs, fileSettings, fileExit;
    //the edit submenu
    private JMenuItem editCut, editCopy, editPaste, editClear, editRemove, editSelectAll, editFind;
    //the help menu
    private JMenuItem helpAbout;
    
    public TextEditor() {
        setEditorTitle(TEXT_EDITOR_TITLE);
        setSize(600, 600);
        //Create an editable text area
        setMainTextArea();
        
        //Add a scroll bar to the text area
        scrollPane = new JScrollPane(getMainTextArea());
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
        fileSave.setEnabled(false);
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
    public void actionPerformed(ActionEvent event) {
        //File > New
        if(event.getSource().equals(fileNew)) {
            MenuActionHelper.fileNew(getMainTextArea());            
        //File > Open
        } else if(event.getSource().equals(fileOpen)) {
            try {
                setCurrentFilePath(MenuActionHelper.fileOpen(this, getMainTextArea()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        //File > Save    
        } else if(event.getSource().equals(fileSave)) {
            try {
                MenuActionHelper.fileSave(getCurrentFilePath(), getMainTextArea());
            } catch (IOException e) {
                e.printStackTrace();
            }
        //File > Save as
        } else if(event.getSource().equals(fileSaveAs)) {
            try {
                setCurrentFilePath(MenuActionHelper.fileSaveAs(this, getMainTextArea()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JMenuItem source = (JMenuItem)(event.getSource());
            String s = "Event source: " + source.getText();
            mainTextArea.append(s + "\n");   
        }
        setEditorTitle("");
        if (getCurrentFilePath() != "") {
            fileSave.setEnabled(true);
        }
    }

    /**
     * Set the text editor title
     * 
     * @param title
     */
    public void setEditorTitle(String title) {
        if (title == "") {
            if (getCurrentFilePath() != "") {
                setTitle(getCurrentFilePath() + " - " + TEXT_EDITOR_TITLE);
            }
        } else {
            setTitle(title);
        }
    }
    
    public JTextArea getMainTextArea() {
        return mainTextArea;
    }

    public void setMainTextArea() {
        mainTextArea = new JTextArea(8, 20);
        mainTextArea.setLineWrap(true);
        mainTextArea.setEditable(true);
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }
    
    /**
     * Gets only file name from current file path
     * 
     * @return File name frOm current file path
     */
    public String getCurrentFileName() {
        Path filePath = Paths.get(getCurrentFilePath());
        String fileName = filePath.getFileName().toString();
        return fileName;
    }
}
