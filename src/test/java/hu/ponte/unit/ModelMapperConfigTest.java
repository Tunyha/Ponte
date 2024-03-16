package hu.ponte.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModelMapperConfigTest {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testModelMapperConfiguration() {
        MatchingStrategy matchingStrategy = modelMapper.getConfiguration().getMatchingStrategy();
        Assertions.assertEquals(MatchingStrategies.STRICT, matchingStrategy);
    }
}