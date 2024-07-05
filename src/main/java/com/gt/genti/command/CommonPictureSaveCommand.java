package com.gt.genti.command;

import com.gt.genti.domain.User;

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
