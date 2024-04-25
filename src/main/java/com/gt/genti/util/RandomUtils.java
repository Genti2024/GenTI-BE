package com.gt.genti.util;

import java.util.List;
import java.util.Random;

public class RandomUtils {
	private static final Random RANDOM = new Random();

	// 정수 범위 내에서 난수 생성
	public static int getRandomInt(int min, int max) {
		return RANDOM.nextInt(max - min + 1) + min;
	}

	// 불린 값 생성
	public static boolean getRandomBoolean() {
		return RANDOM.nextBoolean();
	}

	// 더블 값 생성
	public static double getRandomDouble() {
		return RANDOM.nextDouble();
	}

	// 임의의 길이의 난수 문자열 생성
	public static String getRandomString(int length) {
		return RANDOM.ints('a', 'z' + 1)
			.limit(length)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

	// 배열에서 임의의 요소 선택
	public static <T> T getRandomElement(T[] array) {
		return array[getRandomInt(0, array.length - 1)];
	}

	public static <T> T getRandomElement(List<T> list) {
		return list.get(getRandomInt(0, list.size() - 1));
	}

}
