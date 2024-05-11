package com.gt.genti.dto;

import com.gt.genti.domain.Report;
import com.gt.genti.domain.enums.ReportStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportUpdateDto {
	Long id;
	ReportStatus reportStatus;

}
