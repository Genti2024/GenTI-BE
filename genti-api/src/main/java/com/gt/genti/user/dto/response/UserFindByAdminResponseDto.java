package com.gt.genti.user.dto.response;

import java.time.LocalDateTime;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.deposit.dto.response.DepositFindByAdminResponseDto;
import com.gt.genti.deposit.model.Deposit;
import com.gt.genti.user.model.Sex;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.model.UserStatus;
import com.gt.genti.util.DateTimeUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[User][Admin] 유저조회 by 어드민 응답 dto")
@Getter
@NoArgsConstructor
public class UserFindByAdminResponseDto {
	@Schema(description = "유저DB Id", example = "1")
	Long id;

	@Schema(description = "이메일", example = "example@gmail.com")
	String email;

	@Schema(description = "권한", example = "사용자")
	UserRole userRole;

	@Schema(description = "나이", example = "13")
	int age;

	@Schema(description = "성별", example = "남")
	Sex sex;

	@Schema(description = "상태(활성화, 비활성화)", example = "활성")
	UserStatus userStatus;

	@Schema(description = "가입일자", example = "2024-05-08T10:31:20")
	LocalDateTime createdAt;

	@Schema(description = "누적 주문 횟수(공급자인 경우 null)", example = "6")
	Integer requestTaskCount;

	@Schema(description = "누적 공급 횟수(사용자인 경우 null)", example = "2")
	Integer completedTaskCount;

	@Schema(description = "잔액관련응답 dto 사용자인 경우 null or 0값 존재", nullable = true)
	DepositFindByAdminResponseDto depositResponseDto;

	@Schema(description = "최근 접속일자", example = "2024-06-23T10:15:30")
	LocalDateTime lastLoginDate;

	@Builder
	public UserFindByAdminResponseDto(Long id, String email, UserRole userRole, String birthYear, Sex sex,
		UserStatus userStatus,
		LocalDateTime createdAt, int requestTaskCount, Creator creator,
		Deposit deposit, LocalDateTime lastLoginDate) {
		this.id = id;
		this.email = email;
		this.userRole = userRole;
		this.age = DateTimeUtil.getAge(birthYear);
		this.sex = sex;
		this.userStatus = userStatus;
		this.createdAt = createdAt;
		this.requestTaskCount = requestTaskCount;
		if (creator != null) {
			this.completedTaskCount = creator.getCompletedTaskCount();
		}
		if (deposit != null) {
			this.depositResponseDto = new DepositFindByAdminResponseDto(deposit);
		}
		this.lastLoginDate = lastLoginDate;
	}
}
