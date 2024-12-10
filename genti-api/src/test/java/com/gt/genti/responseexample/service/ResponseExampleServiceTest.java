package com.gt.genti.responseexample.service;


import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.responseexample.model.ResponseExample;
import com.gt.genti.picture.responseexample.repository.ResponseExampleRepository;
import com.gt.genti.responseexample.dto.response.ExampleWithPictureFindResponseDto;
import com.gt.genti.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ResponseExampleServiceTest {

    @Mock
    private ResponseExampleRepository responseExampleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ResponseExampleService responseExampleService;

    private List<ResponseExample> mockResponseExamples;

    @BeforeEach
    void setUp() {
        mockResponseExamples = List.of(
            createExample("예시 프롬프트1", PictureRatio.RATIO_SERO, null),
            createExample("예시 프롬프트2", PictureRatio.RATIO_SERO, null),
            createExample("예시 프롬프트3", PictureRatio.RATIO_GARO, null),
            createExample("예시 프롬프트4", PictureRatio.RATIO_SERO, null),
            createExample("예시 프롬프트5", PictureRatio.RATIO_SERO, null),
            createExample("예시 프롬프트6", PictureRatio.RATIO_SERO, "FREE_ONE"),
            createExample("예시 프롬프트7", PictureRatio.RATIO_SERO, "FREE_ONE"),
            createExample("예시 프롬프트8", PictureRatio.RATIO_GARO, "FREE_ONE"),
            createExample("예시 프롬프트9", PictureRatio.RATIO_SERO, "FREE_ONE"),
            createExample("예시 프롬프트10", PictureRatio.RATIO_SERO, "FREE_ONE"),
            createExample("예시 프롬프트11", PictureRatio.RATIO_SERO, "PAID_ONE"),
            createExample("예시 프롬프트12", PictureRatio.RATIO_SERO, "PAID_ONE"),
            createExample("예시 프롬프트13", PictureRatio.RATIO_GARO, "PAID_ONE"),
            createExample("예시 프롬프트14", PictureRatio.RATIO_SERO, "PAID_ONE"),
            createExample("예시 프롬프트15", PictureRatio.RATIO_SERO, "PAID_ONE"),
            createExample("예시 프롬프트16", PictureRatio.RATIO_SERO, "PAID_TWO"),
            createExample("예시 프롬프트17", PictureRatio.RATIO_SERO, "PAID_TWO"),
            createExample("예시 프롬프트18", PictureRatio.RATIO_GARO, "PAID_TWO"),
            createExample("예시 프롬프트19", PictureRatio.RATIO_SERO, "PAID_TWO"),
            createExample("예시 프롬프트20", PictureRatio.RATIO_SERO, "PAID_TWO")
        );
    }

    @Test
    @DisplayName("모든 피드뷰 예시 사진을 불러왔을 때, 첫 번째 사진의 사진 비율이 RATIO_GARO이면 성공")
    void pictureRatioOfFirstResponseExampleIsRATIOGARO_Then_Success() {
        //given
        given(responseExampleRepository.findAllByPromptOnlyIsFalse()).willReturn(mockResponseExamples);

        //when
        List<ExampleWithPictureFindResponseDto> result = responseExampleService.getAllResponseExamples();

        //then
        assertThat(result.get(0).getPicture().getPictureRatio()).isEqualTo(PictureRatio.RATIO_GARO);
        assertThat(result.stream().skip(1).noneMatch(dto -> dto.getPicture().getPictureRatio() == PictureRatio.RATIO_GARO)).isTrue();
        assertThat(result).hasSize(result.size());
    }

    @Test
    @DisplayName("불러온 생성뷰 예시 사진의 수가 5개이면 성공")
    void sizeOfResponseExampleInGenerateViewIs5_Then_Success() {
        //given
        given(responseExampleRepository.findAllByType(any(String.class))).willReturn(mockResponseExamples);

        //when

        //then
    }

    @Test
    @DisplayName("불러온 생성뷰 예시 사진이 모두 정방형 비율이면 성공")
    void ratioOfAllResponseExampleInGenerateViewIsSquare_Then_Success() {
        //given

        //when

        //then
    }

    private ResponseExample createExample(String prompt, PictureRatio ratio, String type) {
        return ResponseExample.builder()
                .prompt(prompt)
                .pictureRatio(ratio)
                .type(type)
                .build();
    }


}
