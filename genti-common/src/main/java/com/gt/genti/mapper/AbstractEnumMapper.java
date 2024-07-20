package com.gt.genti.mapper;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

public abstract class AbstractEnumMapper<Enum_DB extends Enum<Enum_DB>, Enum_Client extends Enum<Enum_Client>> {

    protected final Map<Enum_DB, Enum_Client> dbToClientMapper;
    protected final Map<Enum_Client, List<Enum_DB>> clientToDbMapper;

    private final Class<Enum_DB> dbEnumClass;
    private final Class<Enum_Client> clientEnumClass;

    protected AbstractEnumMapper(Class<Enum_DB> dbEnumClass, Class<Enum_Client> clientEnumClass, List<SimpleEntry<Enum_DB, Enum_Client>> mappings) {
        this.dbEnumClass = dbEnumClass;
        this.clientEnumClass = clientEnumClass;
        this.dbToClientMapper = new EnumMap<>(dbEnumClass);
        this.clientToDbMapper = new EnumMap<>(clientEnumClass);

        for (SimpleEntry<Enum_DB, Enum_Client> mapping : mappings) {
            dbToClientMapper.put(mapping.getKey(), mapping.getValue());
            clientToDbMapper.computeIfAbsent(mapping.getValue(), k -> new ArrayList<>()).add(mapping.getKey());
        }
    }

    public Enum_Client dbToClient(Enum_DB a) {
        try {
            return dbToClientMapper.get(a);
        } catch (Exception e) {
            throw ExpectedException.withLogging(ResponseCode.EnumMappingFailed,
                dbEnumClass.getSimpleName(),
                clientEnumClass.getSimpleName());
        }
    }

    public List<Enum_DB> clientToDb(Enum_Client b) {
        try {
            return clientToDbMapper.get(b);
        } catch (Exception e) {
            throw ExpectedException.withLogging(ResponseCode.EnumMappingFailed,
                clientEnumClass.getSimpleName(),
                dbEnumClass.getSimpleName());
        }
    }
}
