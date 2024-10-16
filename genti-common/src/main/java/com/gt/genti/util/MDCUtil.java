package com.gt.genti.util;

import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MDCUtil {

    public static final String REQUEST_URI_MDC = "서버 요청 URI 정보";
    public static final String USER_IP_MDC = "사용자 IP 정보";
    public static final String USER_REQUEST_COOKIES = "사용자 Cookie 정보";
    public static final String HEADER_MAP_MDC = "요청 HTTP 헤더 정보";
    public static final String PARAMETER_MAP_MDC = "요청 Parameter 정보";
    public static final String BODY_MDC = "요청 HTTP Body 정보";
    public static final String USER_REQUEST_ORIGIN = "사용자 요청 Origin 정보";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final MDCAdapter mdc = MDC.getMDCAdapter();

    public static void set(String key, String value) {
        mdc.put(key, value);
    }

    public static Object get(String key) {
        return mdc.get(key);
    }

    public static void setJsonValue(String key, Object value) throws JsonProcessingException {
        try {
            if (value != null) {
                String json = objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(value);
                mdc.put(key, json);
            } else {
                mdc.put(key, "내용 없음");
            }
        } catch (JsonProcessingException ex) {
            throw ex;
        }
    }

    public static void clear() {
        MDC.clear();
    }
}
