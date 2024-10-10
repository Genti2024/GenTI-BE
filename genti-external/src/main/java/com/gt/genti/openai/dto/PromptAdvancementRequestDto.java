package com.gt.genti.openai.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PromptAdvancementRequestDto {

	private String model;
	private List<Message> messages;
	private double temperature;
	private int maxTokens;
	private double topP;
	private int frequencyPenalty;
	private int presencePenalty;

	// Getters and setters...

	@Getter
	@Builder
	public static class Message {
		private String role;
		private String content;

		// Getters and setters...
	}

	public static PromptAdvancementRequestDto of(String prompt) {

		return PromptAdvancementRequestDto.builder()
			.model("gpt-4o-mini")
			.messages(getMessageList(prompt))
			.temperature(1)
			.maxTokens(300)
			.topP(1)
			.frequencyPenalty(0)
			.presencePenalty(0)
			.build();
	}

	private static Message makePromptMessage(String prompt) {
		return Message.builder()
			.role("user")
			.content(prompt)
			.build();
	}

	private static List<Message> getMessageList(String prompt) {
		return List.of(Message.builder()
				.role("system")
				.content(
					"\"I want you to help me make prompts for the Stable Diffusion. Stable Diffusion is a text-based image generation model that can create diverse and high-quality images based on users' requests. In order to get the best results from Stable diffusion, you need to follow some guidelines when composing prompts. Here are some tips for writing prompts for Stable Diffusion: Be as specific as possible in the requests. Stable diffusion handles concrete prompts better than abstract or ambiguous ones. For example, instead of “portrait of a woman,” it is better to write “portrait of a Korean woman with brown eyes and red hair in Renaissance style.” Specify specific art styles or materials. If you want to get an image in a certain style or with a certain texture, then specify this in the request. For example, instead of “landscape,” it is better to write “watercolor landscape with mountains and lake.\" Specify specific artists for reference. If you want to get an image similar to the work of some artist, then specify his name in the request. For example, instead of “abstract image,” it is better to write “abstract image in the style of Picasso.” Don't use any pronouns. Avoid using these words: in a, a, an, the, with, of, and, is, of, by Weigh your keywords. You can use token:1.3 to specify the weight of keywords in your query. The greater the weight of the keyword, the more it will affect the result. For example, if you want to get an image of a cat with green eyes and a pink nose, then you can write “a cat:1.5, green eyes:1.3, pink nose:1.” This means that the cat will be the most important element of the image, the green eyes will be less important, and the pink nose will be the least important. Another way to adjust the strength of a keyword is to use () and []. (keyword) increases the strength of the keyword by 1.1 times and is equivalent to (keyword:1.1). [keyword] reduces the strength of the keyword by 0.9 times and corresponds to (keyword:0.9). You can use several of them, as in algebra... The effect is multiplicative. (keyword): 1.1 ((keyword)): 1.21 (((keyword))): 1.33 Similarly, the effects of using multiple [] are as follows [keyword]: 0.9 [[keyword]]: 0.81 [[[keyword]]]: 0.73 I will also give some examples of good prompts for Stable Diffusion so that you can study them and focus on them. Examples: a cute kitten made out of metal, (cyborg:1.1), ([tail | detailed wire]:1.3), (intricate details), hdr, (intricate details, hyperdetailed:1.2), cinematic shot, vignette, centered medical mask, victorian era, cinematography, intricately detailed, crafted, meticulous, magnificent, maximum details, extremely hyper aesthetic a Korean girl, wearing a tie, cupcake in her hands, school, indoors, (soothing tones:1.25), (hdr:1.25), (artstation:1.2), dramatic, (intricate details:1.14), (hyperrealistic 3d render:1.16), (filmic:0.55), (rutkowski:1.1), (faded:1.3) Jane Eyre with headphones, natural skin texture, 24mm, 4k textures, soft cinematic light, adobe lightroom, photolab, hdr, intricate, elegant, highly detailed, sharp focus, ((((cinematic look)))), soothing tones, insane details, intricate details, hyperdetailed, low contrast, soft cinematic light, dim colors, exposure blend, hdr, faded a portrait of a laughing, toxic, muscle, god, elder, (hdr:1.28), bald, hyperdetailed, cinematic, warm lights, intricate details, hyperrealistic, dark radial background, (muted colors:1.38), (neutral colors:1.2) My query may be in other languages. In that case, translate it into English. Your answer is exclusively in English (IMPORTANT!!!), since the model only understands English. Also, you should not copy my request directly in your response, you should compose a new one, observing the format given in the examples. Don't put creating or create front of sentence.")
				.build(),
			Message.builder()
				.role("user")
				.content("약간 높은 앵글에서 촬영한, 하얀 침대가 놓인 조금 어두운 실내 사진")
				.build(),
			Message.builder()
				.role("assistant")
				.content(
					"A slightly elevated view capturing a dimly lit interior scene with a white bed as the focal point, (slightly elevated angle:1.2), (dim lighting:1.3), (white bed:1.4), (interior setting:1.2), (subdued ambiance:1.2), (minimalist decor:1.1), (soft textures:1.1), (serene atmosphere:1.2), (cozy setting:1.1), (subtle shadows:1.2)")
				.build(),
			Message.builder()
				.role("user")
				.content("래쉬가드 상의와 반바지를 입고, 흰색 서핑보드 위에서 파도를 타는 여자 사진")
				.build(),
			Message.builder()
				.role("assistant")
				.content(
					"A photograph of a woman wearing a rash guard top and boardshorts, riding waves on a white surfboard, (woman surfing:1.4), (rash guard top:1.3), (boardshorts:1.3), (white surfboard:1.4), (riding waves:1.3), (action shot:1.2), (dynamic pose:1.2), (surfing attire:1.2), (ocean backdrop:1.2), (adventurous spirit:1.3), (exciting moment:1.2)")
				.build(),
			Message.builder()
				.role("user")
				.content("배경은 공원으로 있었으면 좋겠고 검정색 통청바지에다가 흰색 티셔츠를 입고 잔디위에 앉아서 팔로 하트를 만드는 자세를 만들어주세요")
				.build(),
			Message.builder()
				.role("assistant")
				.content(
					"Incorporate a beach setting, with the individual dressed in a sky-blue dress, standing naturally while gently flipping their hair, (beach backdrop:1.4), (sky-blue dress:1.3), (hair flipping pose:1.3), (natural stance:1.2), (ocean breeze:1.2), (relaxed demeanor:1.2), (gentle motion:1.2), (seaside atmosphere:1.3)")
				.build(),
			Message.builder()
				.role("user")
				.content("배경은 저녁 파리에서 옷은 검정 항공잠바와 검정 조거 팬츠를 입고 자세는 서서 구경하는 모습이면 좋겠어")
				.build(),
			Message.builder()
				.role("assistant")
				.content(
					"Set in an evening Parisian backdrop, the individual clad in a black bomber jacket and black jogger pants admiring the surroundings while standing, (evening in Paris:1.4), (black bomber jacket:1.3), (black jogger pants:1.3), (observing pose:1.3), (cityscape admiration:1.2), (urban exploration:1.2), (elegant attire:1.2), (Parisian ambiance:1.3), (sophisticated look:1.2)")
				.build(),
			Message.builder()
				.role("user")
				.content("하얀옷에 컬러 목도리를 입고 눈밭에서 약간 아련한듯? 청초한듯? 옆을 보며 서있는 사진을 원해요")
				.build(),
			Message.builder()
				.role("assistant")
				.content(
					"A picture of an individual wearing white attire with a colorful scarf, standing in a snowy field with a hint of wistfulness or innocence, looking sideways, (white outfit:1.3), (colorful scarf:1.2), (snowy backdrop:1.4), (hint of wistfulness:1.2), (innocent aura:1.2), (side glance:1.3), (serene expression:1.2), (winter setting:1.3), (ethereal atmosphere:1.2)")
				.build(),
			Message.builder()
				.role("user")
				.content(
					"밤에 공원 벤치에 앉아있었으면 좋겠어요. 의상은 검은색 티에 흰색 후드집업 걸치고 바지는 검은색 청바지 입었으면 합니다. 포즈는 다리를 꼬아서 앉아있는자세로 만들어주세요!")
				.build(),
			Message.builder()
				.role("assistant")
				.content(
					"Seated on a bench in a park at night, the individual adorned in a black t-shirt layered with a white hoodie and black jeans, adopting a pose with legs crossed while sitting, (nighttime park scene:1.4), (black t-shirt:1.3), (white hoodie:1.2), (black jeans:1.3), (leg-crossed pose:1.3), (relaxed seated position:1.2), (park bench setting:1.3), (urban nighttime ambience:1.2), (casual attire:1.2), (subtle night lighting:1.3)")
				.build(),
			makePromptMessage(prompt));

	}

	@Builder
	private PromptAdvancementRequestDto(String model, List<Message> messages, double temperature, int maxTokens,
		double topP,
		int frequencyPenalty, int presencePenalty) {
		this.model = model;
		this.messages = messages;
		this.temperature = temperature;
		this.maxTokens = maxTokens;
		this.topP = topP;
		this.frequencyPenalty = frequencyPenalty;
		this.presencePenalty = presencePenalty;
	}

}
