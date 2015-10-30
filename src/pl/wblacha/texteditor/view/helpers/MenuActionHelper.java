package pl.wblacha.texteditor.view.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import pl.wblacha.texteditor.view.TextEditor;

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
    
    /**
     * Displays a file chooser dialog and loads a text file selected by user into a text area. 
     * 
     * @param textArea
     * @throws IOException
     */
    public static void fileOpen(TextEditor textEditor, JTextArea textArea) throws IOException {
        File selectedFile;
        
        UIManager.put("FileChooser.saveButtonText", "Save");
        UIManager.put("FileChooser.openButtonText", "Open file");
        UIManager.put("FileChooser.cancelButtonText", "Cancel");
        UIManager.put("FileChooser.updateButtonText", "Update");
        UIManager.put("FileChooser.helpButtonText", "Help");
        UIManager.put("FileChooser.fileNameLabelText", "File name:");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Files of type:");
        UIManager.put("FileChooser.upFolderToolTipText", "Up folder");
        UIManager.put("FileChooser.homeFolderToolTipText", "Home folder");
        UIManager.put("FileChooser.newFolderToolTipText", "New folder");
        UIManager.put("FileChooser.listViewButtonToolTipText", "List");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Details");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileFilter(new FileFilter() {
            public boolean accept(File acceptedFile) {
                String fileName = acceptedFile.getName().toLowerCase();
                return fileName.endsWith(".txt") || acceptedFile.isDirectory();
            }

            public String getDescription() {
                return "Text files *.txt";
            }
        });
        int returnValue = fileChooser.showOpenDialog(textEditor);
        if(returnValue != 0) {
            return;
        } 
        selectedFile = fileChooser.getSelectedFile();
        FileInputStream fileIn = new FileInputStream(selectedFile);
        ProgressMonitorInputStream progressIn = new ProgressMonitorInputStream(textEditor, "Reading " + selectedFile.getName(), fileIn);
        InputStreamReader inReader = new InputStreamReader(progressIn);
        BufferedReader in = new BufferedReader(inReader);
        try {
            String line;
            while((line = in.readLine()) != null) {
                textArea.append(line);
                textArea.append("\n");
            }
            in.close();
            textEditor.setTitle(selectedFile.getPath() + " - " + textEditor.getTitle());
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
