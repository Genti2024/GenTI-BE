package com.gt.genti.picturegeneraterequest.dto.request;

import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picturegeneraterequest.command.AdvancedPGREQSaveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "[PGREQ][User] 2인 사진생성요청 생성 dto")
public class AdvancedPGREQSaveRequestDto extends PGREQSaveRequestDto {

    @NotNull
    @Size(max = 3, min = 1, message = "사용자의 얼굴 사진 개수는 최소 1개, 최대 3개입니다.")
    @Schema(description = "추가 인원의 얼굴 사진 리스트")
    List<@NotNull CommonPictureKeyUpdateRequestDto> otherFacePictureList;

    @Override
    public AdvancedPGREQSaveCommand toCommand() {
        return AdvancedPGREQSaveCommand.advancedBuilder()
                .prompt(this.prompt)
                .pictureRatio(this.pictureRatio)
                .facePictureKeyList(this.facePictureList.stream().map(CommonPictureKeyUpdateRequestDto::getKey).toList())
                .otherFacePictureKeyList(this.otherFacePictureList.stream().map(CommonPictureKeyUpdateRequestDto::getKey).toList())
                .build();
    }
}
