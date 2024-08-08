package com.gt.genti.common;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumUtil {

	public static List<PictureGenerateResponseStatus> PGRES_PICTURE_CREATED_LIST = List.of(
		PictureGenerateResponseStatus.COMPLETED, PictureGenerateResponseStatus.ADMIN_SUBMITTED_FINAL);

	public static List<PictureGenerateRequestStatus> PGREQ_PENDING_LIST =
		List.of(
			PictureGenerateRequestStatus.CREATED,
			PictureGenerateRequestStatus.ASSIGNING,
			PictureGenerateRequestStatus.IN_PROGRESS,
			PictureGenerateRequestStatus.MATCH_TO_ADMIN,
			PictureGenerateRequestStatus.AWAIT_USER_VERIFICATION
		);

	public static List<PictureGenerateRequestStatus> PGREQ_IN_PROGRESS_LIST = List.of(
		PictureGenerateRequestStatus.IN_PROGRESS, PictureGenerateRequestStatus.MATCH_TO_ADMIN);

	public static List<PictureGenerateResponseStatus> IN_PROGRESS_PGRES_FOR_CREATOR = List.of(
		PictureGenerateResponseStatus.CREATOR_BEFORE_WORK);

	public static List<PictureGenerateResponseStatus> PGRES_CAN_CHANGE_ADMIN_IN_CHARGE_LIST = List.of(
		PictureGenerateResponseStatus.CREATOR_SUBMITTED_FIRST,
		PictureGenerateResponseStatus.ADMIN_BEFORE_WORK,
		PictureGenerateResponseStatus.ADMIN_IN_PROGRESS);

	public static boolean PICTURE_CREATE_COMPLETED(PictureGenerateResponseStatus status) {
		return PGRES_PICTURE_CREATED_LIST.contains(status);
	}

	public static <E extends Enum<E> & ConvertableEnum> E stringToEnum(Class<E> enumType, String value) {
		return toEnum(enumType, value, false);
	}

	public static <E extends Enum<E> & ConvertableEnum> E stringToEnumIgnoreCase(Class<E> enumType, String value) {
		return toEnum(enumType, value, true);
	}

	private static <E extends Enum<E> & ConvertableEnum> E toEnum(Class<E> enumType, String value, boolean ignoreCase) {
		if (value == null) {
			E enumNullValue = enumType.getEnumConstants()[0].getNullValue();
			if (enumNullValue == null) {
				throw ExpectedException.withLogging(ResponseCode.NotNullableEnum, enumType);
			} else {
				return enumNullValue;
			}
		}
		for (E enumValue : enumType.getEnumConstants()) {
			if (ignoreCase) {
				if (enumValue.getStringValue().equalsIgnoreCase(value)) {
					return enumValue;
				}
			} else {
				if (StringUtils.equals(value, enumValue.getStringValue())) {
					return enumValue;
				}
			}
		}

		throw ExpectedException.withLogging(ResponseCode.DBToEnumFailed, enumType.getSimpleName(), value);
	}

}
