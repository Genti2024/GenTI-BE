package com.gt.genti.dto.admin.response;

import java.time.LocalDateTime;

import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.Report;
import com.gt.genti.domain.enums.ReportStatus;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.other.util.PictureEntityUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "어드민의 신고내역 조회 응답 dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportFindByAdminResponseDto {
	@Schema(description = "신고요청 DB Id")
	Long reportId;
	@Schema(description = "신고자의 email", example = "user@gmail.com")
	String reporterEmail;
	@Schema(description = "공급자의 email", example = "creator@gmail.com")
	String creatorEmail;
	@Schema(description = "사용자에게 전달된(신고된) 최종 사진")
	CommonPictureResponseDto pictureCompleted;
	@Schema(description = "신고 내용", example = "제 얼굴이 아니에요")
	String content;
	@Schema(description = "신고 상태")
	ReportStatus reportStatus;
	@Schema(description = "신고된 사진생성응답 DB Id", example = "1")
	Long pictureGenerateResponseId;
	@Schema(description = "신고일자")
	LocalDateTime createdAt;

	@Builder
	public ReportFindByAdminResponseDto(Report report, String reporterEmail, String creatorEmail,
		Long pictureGenerateResponseId, PictureCompleted pictureCompleted) {
		this.reportId = report.getId();
		this.reporterEmail = reporterEmail;
		this.creatorEmail = creatorEmail;
		this.pictureCompleted = PictureEntityUtils.toCommonResponse(pictureCompleted);
		this.content = report.getContent();
		this.reportStatus = report.getReportStatus();
		this.pictureGenerateResponseId = pictureGenerateResponseId;
		this.createdAt = report.getCreatedAt();
	}
}
