package atm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {
    public final ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T convert(Object objectToBeConverted, T convertToObject){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(objectToBeConverted, (Type)convertToObject.getClass());
    }
}
