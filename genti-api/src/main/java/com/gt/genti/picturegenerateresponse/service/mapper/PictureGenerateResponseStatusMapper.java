package com.gt.genti.picturegenerateresponse.service.mapper;

import static com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;

public class PictureGenerateResponseStatusMapper {

	private static final Map<PictureGenerateResponseStatus, PictureGenerateResponseStatusForAdminMatched> statusToAdminMatchedMapping = new EnumMap<>(
		PictureGenerateResponseStatus.class);
	private static final Map<PictureGenerateResponseStatusForAdminMatched, PictureGenerateResponseStatus> AdminMatchedToStatusMapping = new EnumMap<>(
		PictureGenerateResponseStatusForAdminMatched.class);

	static {
		List<SimpleEntry<PictureGenerateResponseStatus, PictureGenerateResponseStatusForAdminMatched>> mappings = Arrays.asList(
			new SimpleEntry<>(BEFORE_WORK, PictureGenerateResponseStatusForAdminMatched.BEFORE_WORK),
			new SimpleEntry<>(SUBMITTED_FIRST, PictureGenerateResponseStatusForAdminMatched.BEFORE_WORK),
			new SimpleEntry<>(ADMIN_BEFORE_WORK, PictureGenerateResponseStatusForAdminMatched.BEFORE_WORK),
			new SimpleEntry<>(ADMIN_IN_PROGRESS, PictureGenerateResponseStatusForAdminMatched.IN_PROGRESS),
			new SimpleEntry<>(SUBMITTED_FINAL, PictureGenerateResponseStatusForAdminMatched.COMPLETED),
			new SimpleEntry<>(REPORTED, PictureGenerateResponseStatusForAdminMatched.COMPLETED),
			new SimpleEntry<>(COMPLETED, PictureGenerateResponseStatusForAdminMatched.COMPLETED)
		);

		for (SimpleEntry<PictureGenerateResponseStatus, PictureGenerateResponseStatusForAdminMatched> mapping : mappings) {
			statusToAdminMatchedMapping.put(mapping.getKey(), mapping.getValue());
			AdminMatchedToStatusMapping.put(mapping.getValue(), mapping.getKey());
		}
	}

	public static PictureGenerateResponseStatusForAdminMatched toForAdminMatched(PictureGenerateResponseStatus status) {
		return statusToAdminMatchedMapping.get(status);
	}

	public static PictureGenerateResponseStatus toStatus(PictureGenerateResponseStatusForAdminMatched userStatus) {
		return AdminMatchedToStatusMapping.get(userStatus);
	}
}
