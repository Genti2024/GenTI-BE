package com.gt.genti.command;

import com.gt.genti.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreatePicturePoseCommand extends CommonPictureSaveCommand{ }
