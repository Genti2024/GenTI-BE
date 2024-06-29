package com.gt.genti.report.dto.request;

import com.gt.genti.report.model.ReportStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "신고 상태(해결전/후) 수정 Dto")

public class ReportUpdateRequestDto {
	@NotNull
	@Min(1)
	@Schema(description = "신고 엔티티 id", example = "1")
	Long id;
	@NotNull
	@Schema(description = "변경하고자 하는 상태")
	ReportStatus reportStatus;
}
