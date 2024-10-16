package com.gt.genti.response;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorType {

    /**
     * 400 BAD REQUEST (4000 ~ 4099)
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 4000, "잘못된 요청입니다."),
    REQUEST_VALIDATION(HttpStatus.BAD_REQUEST, 4001, "잘못된 요청입니다."),
    INVALID_TYPE(HttpStatus.BAD_REQUEST, 4002, "잘못된 타입이 입력되었습니다."),
    INVALID_MISSING_HEADER(HttpStatus.BAD_REQUEST, 4003, "요청에 필요한 헤더값이 존재하지 않습니다."),
    INVALID_HTTP_REQUEST(HttpStatus.BAD_REQUEST, 4004, "허용되지 않는 문자열이 입력되었습니다."),
    INVALID_IMAGE_EXTENSION(HttpStatus.BAD_REQUEST, 4006, "지원하지 않는 이미지 확장자입니다."),
    ACTIVE_OBJECTIVE_NUMBER_EXCEEDED(HttpStatus.BAD_REQUEST, 4007, "허용된 Objective 개수를 초과하였습니다"),
    ACTIVE_KEY_RESULT_NUMBER_EXCEEDED(HttpStatus.BAD_REQUEST, 4008, "허용된 Key Result 개수를 초과하였습니다"),
    ACTIVE_TASK_NUMBER_EXCEEDED(HttpStatus.BAD_REQUEST, 4009, "허용된 Task 개수를 초과하였습니다."),
    USER_OBJECTIVE_EMPTY(HttpStatus.BAD_REQUEST, 4010, "해당 유저의 Objective가 존재하지 않습니다."),
    INVALID_OBJECTIVE_INDEX(HttpStatus.BAD_REQUEST, 4011, "정상적이지 않은 Objective 위치입니다."),
    INVALID_KEY_RESULT_INDEX(HttpStatus.BAD_REQUEST, 4012, "정상적이지 않은 KeyResult 위치입니다."),
    INVALID_TASK_INDEX(HttpStatus.BAD_REQUEST, 4013, "정상적이지 않은 Task 위치입니다."),
    INVALID_LOG_VALUE(HttpStatus.BAD_REQUEST, 4014, "Log 입력값은 이전 값과 동일할 수 없습니다."),
    INVALID_EXPIRE_AT(HttpStatus.BAD_REQUEST, 4015, "Objective 종료 기간은 오늘보다 이전 날짜일 수 없습니다."),
    INVALID_KEY_RESULT_PERIOD(HttpStatus.BAD_REQUEST, 4016, "KeyResult 기간 설정이 올바르지 않습니다."),
    REQUIRED_EXPIRE_AT(HttpStatus.BAD_REQUEST, 4017, "기간 연장시 목표 종료 기간은 필수 입력값입니다."),
    REQUIRED_KEY_RESULT_VALUE(HttpStatus.BAD_REQUEST, 4018, "KR 수정시 목표값과 체크인 로그는 필수 입력값입니다."),
    NOT_SUPPORTED_LOGIN_PLATFORM(HttpStatus.BAD_REQUEST, 4019, "지원하지 않는 소셜 로그인 플랫폼입니다."),

    /**
     * 401 UNAUTHROZIED (4100 ~ 4199)
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 4100, "인증되지 않았습니다."),
    UNKNOWN_TOKEN(HttpStatus.UNAUTHORIZED, 4100, "인증 토큰이 존재하지 않습니다."),
    INVALID_AUTHORIZATION(HttpStatus.UNAUTHORIZED, 4101, "유효하지 않은 인증 코드입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, 4102, "지원하지 않는 토큰 방식입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, 4103, "유효하지 않은 AccessToken입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 4104, "유효하지 않은 RefreshToken입니다."),
    INVALID_AUTH(HttpStatus.UNAUTHORIZED, 4105, "인증되지 않은 사용자입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 4106, "만료된 Token입니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, 4107, "잘못된 형식의 Token입니다"),
    WRONG_SIGNATURE_TOKEN(HttpStatus.UNAUTHORIZED, 4108, "Signature가 잘못된 Token입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, 4109, "알 수 없는 이유로 요청이 거절되었습니다."),

    /**
     * 403 FORBIDDEN (4300 ~ 4399)
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, 4300, "해당 자원에 접근 권한이 없습니다."),

    /**
     * 404 NOT FOUND (4400 ~ 4499)
     */
    NOT_FOUND(HttpStatus.NOT_FOUND, 4400, "존재하지 않는 리소스입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 4401, "존재하지 않는 유저입니다."),
    NOT_FOUND_OBJECTIVE(HttpStatus.NOT_FOUND, 4402, "존재하지 않는 Objective입니다."),
    NOT_FOUND_KEY_RESULT(HttpStatus.NOT_FOUND, 4403, "존재하지 않는 KeyResult입니다."),
    NOT_FOUND_TASK(HttpStatus.NOT_FOUND, 4404, "존재하지 않는 Task입니다."),
    NOT_FOUND_LOG(HttpStatus.NOT_FOUND, 4405, "존재하지 않는 Log입니다."),

    /**
     * 405 Method Not Allowed (4500 ~ 4599)
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 4500, "잘못된 HTTP method 요청입니다."),

    /**
     * 500 INTERNAL SERVER (5000 ~ 5099)
     */
    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "알 수 없는 서버 에러가 발생했습니다"),
    DISCORD_LOG_APPENDER(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "디스코드 로그 전송에 실패하였습니다"),
    DISCORD_CONTENT(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "디스코드 로그 전송 내용이 존재하지 않습니다."),
    API_CALL(HttpStatus.INTERNAL_SERVER_ERROR, 5003, "외부 API CALL에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }

}
