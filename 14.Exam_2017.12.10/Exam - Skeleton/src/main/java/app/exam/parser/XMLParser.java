package app.exam.parser;

import org.springframework.stereotype.Component;
import app.exam.parser.interfaces.Parser;
import javax.xml.bind.JAXBException;
import java.io.*;

@Component(value = "XMLParser")
public class XMLParser implements Parser {


    @Override
    public <T> T read(Class<T> objectClass, String fileContent) {
        return null;
    }

    @Override
    public <T> String write(T object){
        return null;
    }
}
