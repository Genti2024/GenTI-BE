package com.gt.genti.responseexample.dto.response;

import com.gt.genti.aws.AwsUtils;
import com.gt.genti.picture.responseexample.model.ResponseExample;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[ResponseExample][Admin&User] 사진 생성뷰1에서의 예시 사진 응답 dto")
@Getter
@NoArgsConstructor
public class ExampleWithSquarePicture {
    @Schema(description = "해당 사진의 Url", example = "https://**")
    String url;

    @Schema(description = "에시 프롬프트", example = "벚꽃길에서 벤치에 앉아있어요")
    String prompt;

    @Schema(description = "사진 유형", example = "FREE_ONE")
    String type;

    public ExampleWithSquarePicture(ResponseExample responseExample) {
        this.url = AwsUtils.CLOUDFRONT_BASEURL + "/" + responseExample.getKey();
        this.prompt = responseExample.getExamplePrompt();
    }
}
