package com.gt.genti.picturegenerateresponse.service.mapper;

import static com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;

public class PictureGenerateResponseStatusMapper {

	private static final Map<PictureGenerateResponseStatus, PictureGenerateResponseStatusForAdmin> statusToAdminMatchedMapping = new EnumMap<>(
		PictureGenerateResponseStatus.class);
	private static final Map<PictureGenerateResponseStatusForAdmin, PictureGenerateResponseStatus> AdminMatchedToStatusMapping = new EnumMap<>(
		PictureGenerateResponseStatusForAdmin.class);

	static {
		List<SimpleEntry<PictureGenerateResponseStatus, PictureGenerateResponseStatusForAdmin>> mappings = Arrays.asList(
			new SimpleEntry<>(BEFORE_WORK, PictureGenerateResponseStatusForAdmin.BEFORE_WORK),
			new SimpleEntry<>(SUBMITTED_FIRST, PictureGenerateResponseStatusForAdmin.BEFORE_WORK),
			new SimpleEntry<>(ADMIN_BEFORE_WORK, PictureGenerateResponseStatusForAdmin.BEFORE_WORK),
			new SimpleEntry<>(ADMIN_IN_PROGRESS, PictureGenerateResponseStatusForAdmin.IN_PROGRESS),
			new SimpleEntry<>(SUBMITTED_FINAL, PictureGenerateResponseStatusForAdmin.COMPLETED),
			new SimpleEntry<>(REPORTED, PictureGenerateResponseStatusForAdmin.COMPLETED),
			new SimpleEntry<>(COMPLETED, PictureGenerateResponseStatusForAdmin.COMPLETED)
		);

		for (SimpleEntry<PictureGenerateResponseStatus, PictureGenerateResponseStatusForAdmin> mapping : mappings) {
			statusToAdminMatchedMapping.put(mapping.getKey(), mapping.getValue());
			AdminMatchedToStatusMapping.put(mapping.getValue(), mapping.getKey());
		}
	}

	public static PictureGenerateResponseStatusForAdmin toForAdminMatched(PictureGenerateResponseStatus status) {
		return statusToAdminMatchedMapping.get(status);
	}

	public static PictureGenerateResponseStatus toStatus(PictureGenerateResponseStatusForAdmin userStatus) {
		return AdminMatchedToStatusMapping.get(userStatus);
	}
}
