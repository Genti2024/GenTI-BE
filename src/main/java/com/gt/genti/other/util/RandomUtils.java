package com.gt.genti.other.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomUtils {
	private static final Random RANDOM = new Random();

	private static final List<String> ADJECTIVES = Arrays.asList(
		"부끄러운", "신난", "행복한", "슬픈", "활발한", "차분한", "용감한", "귀여운", "멋진", "즐거운",
		"상냥한", "고요한", "기쁜", "평온한", "기운찬", "단단한", "포근한", "재미있는", "신비한", "환한", "따뜻한", "유쾌한", "깜찍한", "똑똑한", "상쾌한", "행운의",
		"신성한", "용의주도한", "순수한", "깨끗한",
		"사랑스러운", "용맹한", "다정한", "의젓한", "쾌활한", "근면한", "신중한", "낙천적인", "열정적인", "차가운",
		"따사로운", "엄격한", "자상한", "겸손한", "부드러운", "친절한", "적극적인", "긍정적인", "희망찬", "즐거운",
		"명랑한", "조용한", "빛나는", "친근한", "총명한", "의리있는", "정직한", "매력적인"
	);

	private static final List<String> NOUNS = Arrays.asList(
		"우기", "양파", "고양이", "강아지", "토끼", "사자", "호랑이", "곰", "여우", "팬더",
		"나비", "참새", "독수리", "까치", "부엉이", "늑대", "여우비", "도토리", "사슴", "돌고래", "하마", "물개", "코끼리", "원숭이", "두더지", "해바라기", "무지개",
		"햇살", "별빛", "바다", "바람", "강물", "산들바람", "들꽃", "초록이", "구름", "눈꽃", "이슬", "햇님",
		"작은별", "맑은", "흰구름", "산호", "모래", "파도", "조약돌", "별똥별", "노을", "저녁놀", "새벽", "달빛",
		"밤하늘", "은하수", "별구름", "고래", "아침", "봄꽃", "가을", "겨울", "푸른", "하늘", "가랑비", "달팽이"
	);

	public static String generateRandomNickname() {
		String adjective = ADJECTIVES.get(RANDOM.nextInt(ADJECTIVES.size()));
		String noun = NOUNS.get(RANDOM.nextInt(NOUNS.size()));
		return adjective + " " + noun;
	}

	public static void main(String[] args) {
		System.out.println(generateRandomNickname());
	}

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
