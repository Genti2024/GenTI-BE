package com.gt.genti.mapper;

import java.util.AbstractMap.SimpleEntry;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

public abstract class AbstractEnumMapper<E extends Enum<E>, U extends Enum<U>> {

    protected final Map<E, U> aToBMapper;
    protected final Map<U, E> bToAMapper;

    private final Class<E> aEnumClass;
    private final Class<U> bEnumClass;

    protected AbstractEnumMapper(Class<E> aEnumClass, Class<U> bEnumClass, List<SimpleEntry<E, U>> mappings) {
        this.aEnumClass = aEnumClass;
        this.bEnumClass = bEnumClass;
        this.aToBMapper = new EnumMap<>(aEnumClass);
        this.bToAMapper = new EnumMap<>(bEnumClass);

        for (SimpleEntry<E, U> mapping : mappings) {
            aToBMapper.put(mapping.getKey(), mapping.getValue());
            bToAMapper.put(mapping.getValue(), mapping.getKey());
        }
    }

    public U aToB(E a) {
        try {
            return aToBMapper.get(a);
        } catch (Exception e) {
            throw ExpectedException.withLogging(ResponseCode.EnumMappingFailed,
                aEnumClass.getSimpleName(),
                bEnumClass.getSimpleName());
        }
    }

    public E bToA(U b) {
        try {
            return bToAMapper.get(b);
        } catch (Exception e) {
            throw ExpectedException.withLogging(ResponseCode.EnumMappingFailed,
                bEnumClass.getSimpleName(),
                aEnumClass.getSimpleName());
        }
    }
}
