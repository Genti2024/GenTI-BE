package com.gt.genti.dto.admin.response;

import java.time.LocalDateTime;

import com.gt.genti.domain.Report;
import com.gt.genti.domain.enums.ReportStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportFindResponseDto {
	@Schema(name = "id")
	Long id;
	@Schema(name = "reporterEmail")
	String reporterEmail;
	@Schema(name = "creatorEmail")
	String creatorEmail;
	@Schema(name = "content")
	String content;
	@Schema(name = "reportStatus")
	ReportStatus reportStatus;
	@Schema(name = "pictureGenerateResponseId")
	Long pictureGenerateResponseId;
	@Schema(name = "createdAt")
	LocalDateTime createdAt;

	public ReportFindResponseDto(Report report, String reporterEmail, String creatorEmail,
		Long pictureGenerateResponseId) {
		this.id = report.getId();
		this.reporterEmail = reporterEmail;
		this.creatorEmail = creatorEmail;
		this.content = report.getContent();
		this.reportStatus = report.getReportStatus();
		this.pictureGenerateResponseId = pictureGenerateResponseId;
		this.createdAt = report.getCreatedAt();
	}
}
