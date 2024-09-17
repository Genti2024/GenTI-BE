package com.gt.genti.openchat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OpenChatType implements ConvertableEnum {
    OB("OB", "중년층"),
    YB("YB", "어린층");

    private final String stringValue;
    private final String response;

    @JsonCreator
    public static OpenChatType fromString(String value) {
        return EnumUtil.stringToEnum(OpenChatType.class, value);
    }

    @Override
    public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
        return null;
    }
}
