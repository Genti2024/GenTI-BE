package com.gt.genti.discord.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmbedObject {
	private final List<Field> fields = new ArrayList<>();

	private String title;

	private String description;

	private String url;

	private Color color;

	private Footer footer;

	private Thumbnail thumbnail;

	private Image image;

	private Author author;

	public EmbedObject addField(Field field){
		this.fields.add(field);
		return this;
	}
}