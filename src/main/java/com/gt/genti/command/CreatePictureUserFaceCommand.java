package com.gt.genti.command;

import com.gt.genti.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePictureUserFaceCommand {
	String url;
	User user;
}
