package com.gt.genti.picture.command;

import com.gt.genti.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPictureSaveCommand {
	protected String key;
	protected User uploader;
}
