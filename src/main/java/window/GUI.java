package window;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * C:\Users\south\Desktop\Programmers\The_File_Explorier
 */
public class GUI extends JFrame {
    private final String DEFAULT_DIRECTORY = ".";
    private final int DEFAULT_WEIGTH = 640;
    private final int DEFAULT_HEIGTH = 480;
    private Font font = new Font(Font.SANS_SERIF, Font.ITALIC, 22);
    private Font menuFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
    private File choozenDir = new File(DEFAULT_DIRECTORY);
    private FileFilter filter = new window.FileFilter();
    private DefaultListModel listModel = new DefaultListModel();
    private JFileChooser jf;
    private File selectedFile;
    private StringBuffer sb;
    private JMenuBar menuBar;
    public Container contentPane;
    private JMenu file;
    private JMenu newItem;
    private JMenuItem opNotePade;
    private JMenuItem opChooser;
    private JMenuItem ext;
    private File[] files;
    private JTabbedPane tabbedPane;
    private JPanel listPanel;
    private JPanel textPanel;
    private JLabel listLabel;
    private JLabel textLabel;
    private JTextField textField;
    private JList<File> list;
    private JScrollPane listSP;
    private JScrollPane textSP;
    private JTextArea textArea;
    private JButton ok;
    private JButton lookHere;
    private JButton lookInNotePad;
    private JButton refactor;
    private JButton save;
    private char ch;

    public void create_GUI() throws IOException {
        setTitle("TXT_VIEWER_v_1.0");
        setBounds(100, 100, DEFAULT_WEIGTH, DEFAULT_HEIGTH);
        setResizable(false);
        choozen(DEFAULT_DIRECTORY);
        createWindow();
        initWindow();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void choozen(String str) throws java.io.IOException {
        choozenDir = new File(str);
        listModel.removeAllElements();
        files = choozenDir.listFiles(filter);
        choozenDir = null;
        for (int i = 0; i < files.length; i++) {
            listModel.addElement(files[i]);
        }
    }

    public void createWindow() {
        //Меню и действия
        ActionList al = new ActionList(this);
        menuBar = new JMenuBar();
        file = new JMenu("File");
        file.setFont(menuFont);
        newItem = new JMenu("New");
        newItem.setFont(menuFont);
        file.add(newItem);
        opNotePade = new JMenuItem("Create new file on NotePade");
        opNotePade.setFont(menuFont);
        opNotePade.addActionListener(al);
        newItem.add(opNotePade);
        opChooser = new JMenuItem("Choose the file");
        opChooser.setFont(menuFont);
        opChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int res = jf.showOpenDialog(contentPane);
                if (res == jf.APPROVE_OPTION) {
                    try {
                        choozenDir = jf.getSelectedFile();
                        choozen(choozenDir.getPath());

                    } catch (IOException e1) {
                        System.err.println("Error: " + e1.getMessage());
                    }
                    jf.removeAll();
                }
            }
        });
        file.add(opChooser);
        file.addSeparator();
        ext = new JMenuItem("Exit");
        ext.setFont(menuFont);
        ext.addActionListener(al);
        file.add(ext);
        menuBar.add(file);
        //Панели
        listPanel = new JPanel();
        listPanel.setLayout(null);
        listPanel.setBackground(Color.PINK);
        listPanel.setBounds(0, 0, 610, 400);
        textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setBackground(Color.PINK);
        textPanel.setBounds(0, 0, 610, 400);
        listLabel = new JLabel("File list");
        listLabel.setFont(font);
        listLabel.setBounds(20, 0, 90, 20);
        textLabel = new JLabel("Previous View:");
        textLabel.setFont(font);
        textLabel.setBounds(20, 20, 400, 20);
// Панель1
        textField = new JTextField();
        textField.setFont(font);
        textField.setBounds(20, 20, 400, 30);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = e.getActionCommand();
                File file = new File(str);
                try {
                    if (file.exists() && file.isDirectory()) {
                        choozenDir = new File(str);
                        choozen(choozenDir.getPath());
                        System.out.println(str);

                    } else {
                        JOptionPane optionPane = new JOptionPane();
                        sb = new StringBuffer("Неверный адрес директории. Воспользуйтесь стандартным\n поиском через меню: File=>Open");
                        optionPane.showMessageDialog(contentPane, sb, "Неправильный адрес!", JOptionPane.INFORMATION_MESSAGE);
                        textField.setText("");
                        choozen(DEFAULT_DIRECTORY);
                        sb.delete(0, sb.length());
                    }

                } catch (IOException e1) {
                    System.err.println("Error: " + e1.getMessage());
                }
            }
        });
        list = new JList(listModel);
        list.setToolTipText("Выберите файл");
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedFile = list.getSelectedValue();
            }
        });
        listSP = new JScrollPane(list);
        listSP.setBounds(20, 56, 589, 200);

//Панель 2
        textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setEditable(false);
        textSP = new JScrollPane(textArea);
        textSP.setBounds(20, 56, 589, 300);
//Закладки
        tabbedPane = new JTabbedPane();
        contentPane = getContentPane();
        ok = new JButton("Предварительный  просмотр");
        ok.setBounds(20, 260, 300, 30);
        ok.setToolTipText("Выберите файл и нажмите чтобы продолжить");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textLabel.removeAll();
                if (selectedFile.exists() && selectedFile != null) {
                    textLabel.setText("Previous view: " + selectedFile.getName());
                    Str_Catcher str_catcher = new Str_Catcher();
                    textArea.setText(str_catcher.reader(selectedFile.getPath()));
                    selectedFile = null;
                } else {

                }

            }
        });
    }

    private void initWindow() {
        listPanel.add(listLabel);
        listPanel.add(textField);
        listPanel.add(listSP);
        listPanel.add(ok);

        textPanel.add(textLabel);
        textPanel.add(textSP);

        tabbedPane.addTab("Files", listPanel);
        tabbedPane.addTab("Previous View", textPanel);
        contentPane.add(tabbedPane);

        setJMenuBar(menuBar);
    }
}
