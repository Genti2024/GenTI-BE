package com.gt.genti.util;

import static com.gt.genti.error.ResponseCode.*;

import java.time.Duration;
import java.time.LocalDate;

import com.gt.genti.error.ExpectedException;

public class DateTimeUtil {
	public static Long ACCEPTABLE_TIME_MINUTE = 15L;
	public static Long PGRES_LIMIT_HOUR = 4L;
	public static Long PGREQ_LIMIT_HOUR = 6L;

	//소요시간 기준 1시간 이내 2,500원, 2시간 이내 2,000원 4시간 이내 1,000원
	public static Long[][] TIME_REWARD_MAPPER = {
		{60 * 1L, 2500L},
		{60 * 2L, 2000L},
		{60 * 4L, 1000L}
	};

	public static String getTimeString(Duration duration) {
		return String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(),
			duration.toSecondsPart());
	}

	public static Long calculateReward(Long elapsedMinutes) {
		for (Long[] timeReward : TIME_REWARD_MAPPER) {
			if (elapsedMinutes <= timeReward[0]) {
				return timeReward[1];
			}
		}
		throw ExpectedException.withLogging(UnHandledException,
			String.format("요청-응답의 공급자의 응답 소요 시간 계산 중 문제 발생,\n"
				+ "계산된 응답소요시간은 [%d분]", elapsedMinutes));
	}

	public static String getZeroTime() {
		return "00:00:00";
	}

	public static int getAge(LocalDate birthDate) {
		if (birthDate == null) {
			return -1;
		}
		LocalDate now = LocalDate.now();
		int age = now.getYear() - birthDate.getYear();

		// 현재 날짜가 생일 전이라면 만나이를 하나 줄입니다.
		if (now.getMonthValue() < birthDate.getMonthValue() ||
			(now.getMonthValue() == birthDate.getMonthValue() &&
				now.getDayOfMonth() < birthDate.getDayOfMonth())) {
			age--;
		}
		return age;
	}

	public static int getAge(String birthDate) {
		if (birthDate == null) {
			return -1;
		}
		LocalDate now = LocalDate.now();
		String[] splittedBirthDate = birthDate.split("-");
		int year = Integer.parseInt(splittedBirthDate[0]);
		int month = Integer.parseInt(splittedBirthDate[1]);
		int day = Integer.parseInt(splittedBirthDate[2]);
		int age = now.getYear() - year;

		// 현재 날짜가 생일 전이라면 만나이를 하나 줄입니다.
		if (now.getMonthValue() < month ||
			(now.getMonthValue() == month &&
				now.getDayOfMonth() < day)) {
			age--;
		}
		return age;
	}
}
