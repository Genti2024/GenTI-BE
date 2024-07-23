package com.gt.genti.report.service;

import java.util.Comparator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.completed.model.PictureCompleted;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.repository.PictureGenerateResponseRepository;
import com.gt.genti.report.dto.request.ReportCreateRequestDto;
import com.gt.genti.report.dto.request.ReportUpdateRequestDto;
import com.gt.genti.report.dto.response.ReportFindByAdminResponseDto;
import com.gt.genti.report.model.Report;
import com.gt.genti.report.model.ReportStatus;
import com.gt.genti.report.repository.ReportRepository;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final ReportRepository reportRepository;
	private final UserRepository userRepository;

	public Boolean createReport(Long userId, ReportCreateRequestDto reportCreateRequestDto) {
		User foundUser = getUserById(userId);
		Long pictureGenerateResponseId = reportCreateRequestDto.getPictureGenerateResponseId();
		PictureGenerateResponse findPictureGenerateResponse = pictureGenerateResponseRepository.findById(
			pictureGenerateResponseId).orElseThrow(() -> ExpectedException.withLogging(
			ResponseCode.PictureGenerateResponseNotFound));

		Report createReport = new Report(foundUser, findPictureGenerateResponse, reportCreateRequestDto.getContent());
		reportRepository.save(createReport);

		findPictureGenerateResponse.reported();
		findPictureGenerateResponse.getRequest().reported();
		return true;
	}

	private User getUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound, userId));
	}

	public Page<ReportFindByAdminResponseDto> getAllReports(Pageable pageable) {
		return reportRepository.findAll(pageable).map(
			ReportService::mapToResponseDto);
	}

	private static ReportFindByAdminResponseDto mapToResponseDto(Report report) {
		return ReportFindByAdminResponseDto.builder()
			.report(report)
			.reporterEmail(report.getCreatedBy().getEmail())
			.creatorEmail(report.getPictureGenerateResponse().getCreator().getUser().getEmail())
			.pictureCompleted(
				report.getPictureGenerateResponse()
					.getCompletedPictureList()
					.stream()
					.min(Comparator.comparing(
						PictureCompleted::getId))
					.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.PictureCompletedNotFound)))
			.pictureGenerateResponseId(report.getPictureGenerateResponse().getId())
			.build();
	}

	public Page<ReportFindByAdminResponseDto> getAllReportsByReportStatus(ReportStatus reportStatus,
		Pageable pageable) {
		return reportRepository.findAllByReportStatusIs(reportStatus, pageable).map(
			ReportService::mapToResponseDto);
	}

	public Page<ReportFindByAdminResponseDto> getReportsByUserEmail(String email, String statusString, Pageable pageable) {
		User foundUser = userRepository.findByEmail(email).orElseThrow(()->ExpectedException.withLogging(ResponseCode.UserNotFoundByEmail, email));
		if ("ALL".equalsIgnoreCase(statusString)) {
			return reportRepository.findAllByCreatedBy(foundUser, pageable).map(ReportService::mapToResponseDto);
		} else {
			return reportRepository.findAllByCreatedByAndReportStatus(foundUser, ReportStatus.valueOf(statusString), pageable).map(ReportService::mapToResponseDto);
		}
	}

	public Boolean updateReport(ReportUpdateRequestDto reportUpdateRequestDto) {
		Long id = reportUpdateRequestDto.getId();
		ReportStatus status = reportUpdateRequestDto.getReportStatus();
		Report findReport = reportRepository.findById(id)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.ReportNotFound));
		findReport.updateStatus(status);
		return true;
	}
}
