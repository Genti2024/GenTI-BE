package com.gt.genti.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.Report;
import com.gt.genti.domain.enums.ReportStatus;
import com.gt.genti.dto.ReportCreateRequestDto;
import com.gt.genti.dto.ReportResponseDto;
import com.gt.genti.dto.ReportUpdateDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.PictureGenerateResponseRepository;
import com.gt.genti.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;
	private final ReportRepository reportRepository;

	public Boolean createReport(ReportCreateRequestDto reportCreateRequestDto) {
		Long pictureGenerateResponseId = reportCreateRequestDto.getPictureGenerateResponseId();
		PictureGenerateResponse findPictureGenerateResponse = pictureGenerateResponseRepository.findById(
			pictureGenerateResponseId).orElseThrow(() -> new ExpectedException(
			ErrorCode.PictureGenerateResponseNotFound));

		Report createReport = new Report(findPictureGenerateResponse, reportCreateRequestDto.getContent());

		reportRepository.save(createReport);

		return true;
	}

	public List<ReportResponseDto> getAllReports() {
		return reportRepository.findAllByOrderByReportStatusAndCreatedAt();
	}

	@Transactional
	public Boolean updateReport(ReportUpdateDto reportUpdateDto) {
		Long id = reportUpdateDto.getId();
		ReportStatus status = reportUpdateDto.getReportStatus();
		Report findReport = reportRepository.findById(id).orElseThrow(() -> new ExpectedException(ErrorCode.ReportNotFound));
		findReport.updateStatus(status);
		return true;
	}
}
