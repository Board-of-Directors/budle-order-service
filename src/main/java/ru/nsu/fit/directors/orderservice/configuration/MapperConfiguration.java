package ru.nsu.fit.directors.orderservice.configuration;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nsu.fit.directors.orderservice.enums.OrderStatus;

/**
 * Mapper configuration for our system.
 */
@Configuration
public class MapperConfiguration {

    private final AbstractConverter<OrderStatus, Integer> convertOrderStatus =
        new AbstractConverter<>() {
            @Override
            protected Integer convert(OrderStatus source) {
                return source.getStatus();
            }
        };


    /**
     * Bean that allows dependency injection for model mapper.
     *
     * @return instance of model mapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addConverter(convertOrderStatus);
        return mapper;
    }
}
