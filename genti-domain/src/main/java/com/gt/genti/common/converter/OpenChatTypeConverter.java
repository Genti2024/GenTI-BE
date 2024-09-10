package com.gt.genti.common.converter;

import com.gt.genti.openchat.model.OpenChatType;
import jakarta.persistence.Converter;

@Converter
public class OpenChatTypeConverter extends DefaultEnumDBConverter<OpenChatType> {

    public OpenChatTypeConverter() {
        super(OpenChatType.class);
    }

}