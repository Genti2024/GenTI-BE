package com.gt.genti.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.Report;
import com.gt.genti.domain.enums.ReportStatus;
import com.gt.genti.dto.admin.request.ReportSaveRequestDto;
import com.gt.genti.dto.admin.response.ReportFindResponseDto;
import com.gt.genti.dto.admin.request.ReportUpdateRequestDto;
import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.PictureGenerateResponseRepository;
import com.gt.genti.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final ReportRepository reportRepository;

	public Boolean createReport(ReportSaveRequestDto reportSaveRequestDto) {
		Long pictureGenerateResponseId = reportSaveRequestDto.getPictureGenerateResponseId();
		PictureGenerateResponse findPictureGenerateResponse = pictureGenerateResponseRepository.findById(
			pictureGenerateResponseId).orElseThrow(() -> new ExpectedException(
			DomainErrorCode.PictureGenerateResponseNotFound));

		Report createReport = new Report(findPictureGenerateResponse, reportSaveRequestDto.getContent());

		reportRepository.save(createReport);

		return true;
	}

	public List<ReportFindResponseDto> getAllReports() {
		return reportRepository.findAllByOrderByReportStatusAndCreatedAt();
	}

	@Transactional
	public Boolean updateReport(ReportUpdateRequestDto reportUpdateRequestDto) {
		Long id = reportUpdateRequestDto.getId();
		ReportStatus status = reportUpdateRequestDto.getReportStatus();
		Report findReport = reportRepository.findById(id).orElseThrow(() -> new ExpectedException(DomainErrorCode.ReportNotFound));
		findReport.updateStatus(status);
		return true;
	}
}
