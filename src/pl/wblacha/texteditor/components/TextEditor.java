package pl.wblacha.texteditor.components;

import java.awt.Container;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pl.wblacha.texteditor.components.helpers.MenuActionHelper;

public class TextEditor extends JFrame implements ActionListener {
    public final static String TEXT_EDITOR_TITLE = "Text editor - developed by Wojciech Blacha 2015";
    public final static String NEW_FILE_NAME = "new file.txt";
    
    protected JTextArea editorTextArea;
    protected JScrollPane scrollPane;
    protected String currentFilePath = "";
    protected JTabbedPane tabs;
    protected int previousTabIndex;
    protected List<Object> tabItems;
    protected String mainTextAreaContent = "";
    protected Map<String, Integer> filePathTabIndex = new HashMap<String, Integer>();
    protected String userAction = "";
    protected TabItem tabItem;
    
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
        setEditorTextArea();
        
        tabItems = new ArrayList<Object>();
        //Add a scroll bar to the text area
        scrollPane = new JScrollPane(getEditorTextArea());
        //Add the scroll pane into the content pane
        Container contentPane = getContentPane();
        contentPane.add(scrollPane, "Center");
        //Add the menu bar to the frame
        setJMenuBar(createMenuBar()); 
        
        tabs = new JTabbedPane();
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        //Add a change listener to the instance of JTabbedPane to detect the tab selection changes
        tabs.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) { 
                savePreviousTabContent();
                //Get the selected tab index
                int tabCurrentIndex = tabs.getSelectedIndex();
                setPreviousTabIndex(tabCurrentIndex);                    
                tabItem = (TabItem) tabItems.get(tabCurrentIndex);                
                setCurrentFilePath(tabItem.getFilePath());
                setEditorTextAreaContent(tabItem.getTabContent());                
                setUserAction("");
                setEditorTitle("");
                setDisableEnableMenuItem();
            }
        });
        //Create and add first tab to the panel with tabs
        createTab();
        //Add the panel with tabs at the bottom of window
        contentPane.add(tabs, "South"); 
    }
    
    public JTextArea getEditorTextArea() {
        return editorTextArea;
    }

    public void setEditorTextArea() {
        editorTextArea = new JTextArea(8, 20);
        editorTextArea.setLineWrap(true);
        editorTextArea.setEditable(true);
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }      
    
    public int getPreviousTabIndex() {
        return previousTabIndex;
    }

    public void setPreviousTabIndex(int previousTabIndex) {
        this.previousTabIndex = previousTabIndex;
    }

    public String getEditorTextAreaContent() {
        return editorTextArea.getText();
    }

    public void setEditorTextAreaContent(String mainTextAreaContent) {
        editorTextArea.setText(mainTextAreaContent);
    }    
    
    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
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
    public void actionPerformed(ActionEvent event) {
        savePreviousTabContent();
        //File > New
        if (event.getSource().equals(fileNew)) {
            setUserAction("file_new");
            setCurrentFilePath("");
            MenuActionHelper.fileNew(getEditorTextArea());
            createTab();
        //File > Open
        } else if (event.getSource().equals(fileOpen)) {
            try {
                setUserAction("file_open");
                setCurrentFilePath(MenuActionHelper.fileOpen(this, getEditorTextArea()));
                createTab();   
            } catch (IOException e) {
                e.printStackTrace();
            }
        //File > Save    
        } else if (event.getSource().equals(fileSave)) {
            try {
                MenuActionHelper.fileSave(getCurrentFilePath(), getEditorTextArea());
            } catch (IOException e) {
                e.printStackTrace();
            }
        //File > Save as
        } else if (event.getSource().equals(fileSaveAs)) {
            try {
                setCurrentFilePath(MenuActionHelper.fileSaveAs(this, getEditorTextArea()));
                setTabTitle("", tabs.getSelectedIndex());
                filePathTabIndex.put(getCurrentFilePath(), tabs.getSelectedIndex());
                tabItem = (TabItem) tabItems.get(tabs.getSelectedIndex());                
                tabItem.setFilePath(getCurrentFilePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JMenuItem source = (JMenuItem)(event.getSource());
            String s = "Event source: " + source.getText();
            editorTextArea.append(s + "\n");   
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
                title = getCurrentFilePath();
            } else {
                title = NEW_FILE_NAME;
            }
            setTitle(title + " - " + TEXT_EDITOR_TITLE);
        } else {
            setTitle(title);
        }
    }
        
    /**
     * Gets only file name from the current file path
     * 
     * @return File name from the current file path
     */
    public String getCurrentFileName() {
        Path filePath = Paths.get(getCurrentFilePath());
        String fileName = filePath.getFileName().toString();
        return fileName;
    }
    
    /**
     *  Adds new tab with title. If a user clicks on the "File> New", it will create a new tab.
     *  If a user opens file using the 'File > Open file...'. it will create a new tab 
     *  and combines the contenst of this file with this tab.
     *  If a user opens file that is currently open, the existing tab will be selected.  
     * 
     */
    public void createTab() {
        String filename = getCurrentFileName();
        if (filename.equals("")) {
            filename = NEW_FILE_NAME;
        }
        Integer value = filePathTabIndex.get(getCurrentFilePath());
        if (value != null) {
            tabs.setSelectedIndex(value);
        } else {
            //save path of current file and the content of editor text area
            tabItems.add(new TabItem(getCurrentFilePath(), getEditorTextAreaContent()));
            //add tab   
            tabs.addTab(filename, null);
            tabs.setSelectedIndex(tabs.getTabCount() - 1);            
            if (getCurrentFilePath() != "") {
                filePathTabIndex.put(getCurrentFilePath(), tabs.getSelectedIndex());
            }       
        }        
    }
        
    /**
     * Saves content of the editor text area for the previous tab.
     * 
     */
    public void savePreviousTabContent() {
        if (getUserAction().equals("")) {
            tabItem = (TabItem) tabItems.get(getPreviousTabIndex());
            tabItem.setTabContent(getEditorTextAreaContent());
        }
    }
    
    /**
     * Sets the title at the tab index.
     * 
     * @param tabTitle
     * @param tabIndex
     */
    public void setTabTitle(String tabTitle, int tabIndex) {
        if (tabTitle.equals("")) {
            tabTitle = getCurrentFileName();
        }
        tabs.setTitleAt(tabIndex, tabTitle);
    }
    
    /**
     * Disables or enables 'File > Save' menu item.
     * If a file is created using 'File > New file', then 'File > Save' is disabled.
     * If a user saves a new file using 'File > Save as', then 'File > Save' is enabled.
     * 
     */
    public void setDisableEnableMenuItem() {
        if (getCurrentFilePath() != "") {
            fileSave.setEnabled(true);
        } else {
            fileSave.setEnabled(false);
        }
    }   
}
