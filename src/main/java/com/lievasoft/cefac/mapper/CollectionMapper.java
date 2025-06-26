package com.lievasoft.cefac.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class CollectionMapper {

    public <T, R> List<R> transform(List<T> collection, Function<T, R> mapper) {
        return collection.stream()
                .map(mapper)
                .toList();
    }
}
