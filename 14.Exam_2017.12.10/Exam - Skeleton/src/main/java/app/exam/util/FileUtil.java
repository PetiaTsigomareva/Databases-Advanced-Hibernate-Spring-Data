package app.exam.util;

import java.io.IOException;

public interface FileUtil {

    String readFile(String filePath) throws IOException;

    void writeFile(String filePath, String content) throws IOException;
}
