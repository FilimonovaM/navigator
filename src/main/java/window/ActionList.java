package window;

import java.awt.event.*;

public class ActionList extends MouseAdapter
        implements ActionListener, KeyListener {
    private final GUI frame;

    public ActionList(GUI frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Create new file on NotePade":
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("NotePad.exe");
                } catch (java.io.IOException e) {
                    System.err.print("Error: " + e.getMessage());
                }
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
