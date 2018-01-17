package window;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Str_Catcher {
    private File chosenFile;
    private StringBuffer sb;
    private char ch;

    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        gui.create_GUI();
    }

    public String reader(String str) {
        String strText;
        chosenFile = new File(str);
        if (chosenFile.canRead()) {
            try (RandomAccessFile fileReader = new RandomAccessFile(chosenFile.getPath(), "r")) {
                int i = fileReader.read();
                sb = new StringBuffer();
                System.out.println(str);
                while (i != -1) {
                    i = fileReader.read();
                    ch = (char) i;
                    sb.append(ch);
                }
                fileReader.close();
            } catch (java.io.IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
            strText = sb.toString();
            System.out.print(strText);
            sb.delete(0, sb.length());

        } else {
            strText = "Файл нечитабелен";
        }
        chosenFile = null;
        return strText;
    }
}
