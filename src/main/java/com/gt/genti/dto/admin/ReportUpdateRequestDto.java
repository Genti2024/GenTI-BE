package com.gt.genti.dto.admin;

import com.gt.genti.domain.enums.ReportStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportUpdateRequestDto {
	Long id;
	ReportStatus reportStatus;
}
