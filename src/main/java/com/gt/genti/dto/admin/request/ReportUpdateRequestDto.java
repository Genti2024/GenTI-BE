package com.gt.genti.dto.admin.request;

import com.gt.genti.domain.enums.ReportStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "ㅁㄴㅇㄹ")

public class ReportUpdateRequestDto {
	@NotNull
	@Schema(name = "id")
	Long id;
	@NotNull
	@Schema(name = "reportStatus")
	ReportStatus reportStatus;
}
