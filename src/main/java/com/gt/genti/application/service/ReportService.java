package com.gt.genti.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.Report;
import com.gt.genti.dto.ReportCreateRequestDto;
import com.gt.genti.dto.ReportResponseDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.repository.PictureGenerateResponseRepository;
import com.gt.genti.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	PictureGenerateResponseRepository pictureGenerateResponseRepository;
	ReportRepository reportRepository;

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
}
