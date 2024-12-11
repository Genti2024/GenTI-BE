package com.gt.genti.responseexample.dto.response;

import com.gt.genti.aws.AwsUtils;
import com.gt.genti.common.picture.model.PictureEntity;
import com.gt.genti.picture.PictureRatio;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "[Picture][Anonymous] 임시 공통 사진 응답 dto")
public class TempCommonPictureResponseDto {

    @Schema(description = "해당 사진의 Entity id", example = "1")
    Long id;

    @Schema(description = "해당 사진의 Url", example = "https://**")
    String url;

    @Schema(description = "해당 사진의 Key값", example = "USER_UPLOADED_IMAGE/**")
    String key;

    @Schema(description = "생성된 사진의 비율, 사진의 비율이 정해지지 않은 경우 \"NONE\"")
    PictureRatio pictureRatio;

    @Builder
    public TempCommonPictureResponseDto(Long id, String key, PictureRatio pictureRatio) {
        this.id = id;
        this.key = key;
        this.url = AwsUtils.CLOUDFRONT_BASEURL + "/" + key;
        this.pictureRatio = pictureRatio;
    }

    public static TempCommonPictureResponseDto of(PictureEntity pictureEntity) {
        if (pictureEntity == null) {
            return null;
        }
        return TempCommonPictureResponseDto.builder()
                .id(pictureEntity.getId())
                .key(pictureEntity.getKey())
                .pictureRatio(pictureEntity.getPictureRatio())
                .build();
    }

}
