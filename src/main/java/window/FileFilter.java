package window;

import java.io.File;

/**
 *
 * @author uriyz
 */
public class FileFilter implements java.io.FileFilter {
    @Override
    public boolean accept(File file) {
        return file.isFile();
    }
}
