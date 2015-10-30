package pl.wblacha.texteditor;

import javax.swing.JFrame;

import pl.wblacha.texteditor.view.TextEditor;

public class Main {
    /**
     * Create and show the application's GUI. For thread safety,
     * this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowTextEditor() {
        //Create and set up the window.
        JFrame frame = new TextEditor();
        //Display the window.
        frame.setVisible(true);
    }
        
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowTextEditor();
            }
        });
    }

}
