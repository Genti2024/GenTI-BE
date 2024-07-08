package com.gt.genti.discord;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.gt.genti.discord.model.Author;
import com.gt.genti.discord.model.EmbedObject;
import com.gt.genti.discord.model.Field;
import com.gt.genti.discord.model.Footer;
import com.gt.genti.discord.model.Image;
import com.gt.genti.discord.model.DiscordMessageJson;
import com.gt.genti.discord.model.Thumbnail;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

import lombok.Getter;

@Getter
public class DiscordWebHook {

	private final List<EmbedObject> embeds = new ArrayList<>();
	private String username;
	private String avatarUrl;
	private boolean tts;



	public DiscordWebHook(String username, String avatarUrl, boolean tts) {
		this.username = username;
		this.avatarUrl = avatarUrl;
		this.tts = tts;
	}

	public void addEmbed(EmbedObject embed) {
		this.embeds.add(embed);
	}

	private DiscordMessageJson initializerDiscordSendForJsonObject() {
		DiscordMessageJson json = new DiscordMessageJson();
		json.put("username", this.username);
		json.put("avatar_url", this.avatarUrl);
		json.put("tts", this.tts);
		return json;
	}

	public DiscordMessageJson createDiscordEmbedObject() {
		if (embeds.isEmpty()) {
			throw ExpectedException.withLogging(ResponseCode.DiscordAppenderException);
		}

		List<DiscordMessageJson> embedObjects = new ArrayList<>();

		for (EmbedObject embed : embeds) {
			DiscordMessageJson jsonEmbed = new DiscordMessageJson();

			jsonEmbed.put("title", embed.getTitle());
			jsonEmbed.put("description", embed.getDescription());
			jsonEmbed.put("url", embed.getUrl());

			processDiscordEmbedColor(embed, jsonEmbed);
			processDiscordEmbedFooter(embed.getFooter(), jsonEmbed);
			processDiscordEmbedImage(embed.getImage(), jsonEmbed);
			processDiscordEmbedThumbnail(embed.getThumbnail(), jsonEmbed);
			processDiscordEmbedAuthor(embed.getAuthor(), jsonEmbed);
			processDiscordEmbedMessageFields(embed.getFields(), jsonEmbed);

			embedObjects.add(jsonEmbed);
		}
		DiscordMessageJson message = initializerDiscordSendForJsonObject();
		message.put("embeds", embedObjects.toArray());

		return message;
	}

	private void processDiscordEmbedColor(EmbedObject embed, DiscordMessageJson jsonEmbed) {
		if (embed.getColor() != null) {
			Color color = embed.getColor();
			int rgb = color.getRed();
			rgb = (rgb << 8) + color.getGreen();
			rgb = (rgb << 8) + color.getBlue();

			jsonEmbed.put("color", rgb);
		}
	}

	private void processDiscordEmbedFooter(Footer footer, DiscordMessageJson jsonEmbed) {
		if (footer != null) {
			DiscordMessageJson jsonFooter = new DiscordMessageJson();
			jsonFooter.put("text", footer.getText());
			jsonFooter.put("icon_url", footer.getIconUrl());
			jsonEmbed.put("footer", jsonFooter);
		}
	}

	private void processDiscordEmbedImage(Image image, DiscordMessageJson jsonEmbed) {
		if (image != null) {
			DiscordMessageJson jsonImage = new DiscordMessageJson();
			jsonImage.put("url", image.getUrl());
			jsonEmbed.put("image", jsonImage);
		}
	}

	private void processDiscordEmbedThumbnail(Thumbnail thumbnail, DiscordMessageJson jsonEmbed) {
		if (thumbnail != null) {
			DiscordMessageJson jsonThumbnail = new DiscordMessageJson();
			jsonThumbnail.put("url", thumbnail.getUrl());
			jsonEmbed.put("thumbnail", jsonThumbnail);
		}
	}

	private void processDiscordEmbedAuthor(Author author, DiscordMessageJson jsonEmbed) {
		if (author != null) {
			DiscordMessageJson jsonAuthor = new DiscordMessageJson();
			jsonAuthor.put("name", author.getName());
			jsonAuthor.put("url", author.getUrl());
			jsonAuthor.put("icon_url", author.getIconUrl());
			jsonEmbed.put("author", jsonAuthor);
		}
	}

	private void processDiscordEmbedMessageFields(List<Field> fields, DiscordMessageJson jsonEmbed) {
		List<DiscordMessageJson> jsonFields = new ArrayList<>();

		for (Field field : fields) {
			DiscordMessageJson jsonField = new DiscordMessageJson();

			jsonField.put("name", field.getName());
			jsonField.put("value", field.getValue());
			jsonField.put("inline", field.isInline());

			jsonFields.add(jsonField);
		}

		jsonEmbed.put("fields", jsonFields.toArray());
	}
}
