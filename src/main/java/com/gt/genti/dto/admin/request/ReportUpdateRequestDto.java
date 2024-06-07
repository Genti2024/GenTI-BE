package com.gt.genti.dto.admin.request;

import com.gt.genti.domain.enums.ReportStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportUpdateRequestDto {
	@NotNull
	Long id;
	@NotNull
	ReportStatus reportStatus;
}
