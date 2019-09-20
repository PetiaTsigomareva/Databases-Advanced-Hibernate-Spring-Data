package app.exam.parser;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import app.exam.parser.interfaces.ModelParser;

@Component
public class ModelParserImpl implements ModelParser {

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass) {
        return null;
    }

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass, PropertyMap<S, D> propertyMap) {
        return null;
    }
}
