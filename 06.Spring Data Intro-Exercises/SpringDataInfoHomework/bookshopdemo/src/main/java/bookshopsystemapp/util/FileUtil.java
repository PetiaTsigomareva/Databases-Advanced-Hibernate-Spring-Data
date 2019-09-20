package bookshopsystemapp.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileUtil {
    String [] getFileContent(String path)throws FileNotFoundException,IOException;
}
