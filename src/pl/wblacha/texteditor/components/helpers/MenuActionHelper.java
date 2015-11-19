package pl.wblacha.texteditor.components.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import pl.wblacha.texteditor.components.TextEditor;

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
    public static String fileOpen(TextEditor textEditor, JTextArea textArea) throws IOException {
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
        if (returnValue != 0) {
            return "";
        } 
        selectedFile = fileChooser.getSelectedFile();
        FileInputStream fileIn = new FileInputStream(selectedFile);
        ProgressMonitorInputStream progressIn = new ProgressMonitorInputStream(textEditor, "Reading " + selectedFile.getName(), fileIn);
        InputStreamReader inReader = new InputStreamReader(progressIn);
        BufferedReader in = new BufferedReader(inReader);
        textArea.setText("");
        try {
            String line;
            while ((line = in.readLine()) != null) {
                textArea.append(line);
                textArea.append("\n");
            }
            in.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return selectedFile.getPath();
    }

    /**
     * Saves data from text area to a file
     * 
     * @param filePath
     * @param textArea
     * @throws IOException
     */
    public static void fileSave(String filePath, JTextArea textArea) throws IOException {
        if (filePath == "") {
            return;
        }
        
        PrintWriter printWriter = null;
        
        try {
            String dataToSave = textArea.getText();
            printWriter = new PrintWriter(new FileOutputStream(filePath), true);
            printWriter.print(dataToSave);
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
    
    /**
     * Displays a save file dialog box.
     * In this dialog box user can choose a location and type a file name to be saved.
     * 
     * @param textEditor
     * @param textArea
     * @return Path to the saved file
     * @throws IOException
     */
    public static String fileSaveAs(TextEditor textEditor, JTextArea textArea) throws IOException {
        File selectedFile = null;
        String filePath;
        JFileChooser fileChooser = new JFileChooser();
        
        int returnValue = fileChooser.showDialog(textEditor, "Save as...");
        if(returnValue != 0) {
            return "";
        }
        selectedFile = fileChooser.getSelectedFile();
        filePath = selectedFile.getPath();
        fileSave(filePath, textArea);
           
        return filePath;
    }
}
