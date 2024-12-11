package com.gt.genti.responseexample.service;


import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.responseexample.model.ResponseExample;
import com.gt.genti.picture.responseexample.repository.ResponseExampleRepository;
import com.gt.genti.responseexample.dto.response.ExampleWithPictureFindResponseDto;
import com.gt.genti.responseexample.dto.response.ExampleWithSquarePicture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ResponseExampleServiceTest {

    @Mock
    private ResponseExampleRepository responseExampleRepository;

    @InjectMocks
    private ResponseExampleService responseExampleService;

    private List<ResponseExample> mockResponseExamples;

    @BeforeEach
    void setUp() {
        mockResponseExamples = List.of(
            createExample("피드뷰 - 예시 프롬프트1", PictureRatio.RATIO_SERO, null, FALSE), // 피드뷰
            createExample("피드뷰 - 예시 프롬프트2", PictureRatio.RATIO_SERO, null, FALSE),
            createExample("피드뷰 - 예시 프롬프트3", PictureRatio.RATIO_SERO, null, FALSE),
            createExample("피드뷰 - 예시 프롬프트4", PictureRatio.RATIO_SERO, null, FALSE),
            createExample("피드뷰 - 예시 프롬프트5", PictureRatio.RATIO_SERO, null, FALSE),
            createExample("피드뷰 - 예시 프롬프트6", PictureRatio.RATIO_SERO, null, FALSE),
            createExample("피드뷰 - 예시 프롬프트7", PictureRatio.RATIO_SERO, null, FALSE),
            createExample("피드뷰 - 예시 프롬프트8", PictureRatio.RATIO_SERO, null, FALSE),
            createExample("피드뷰 - 예시 프롬프트9", PictureRatio.RATIO_GARO, null, FALSE),
            createExample("피드뷰 - 예시 프롬프트10", PictureRatio.RATIO_GARO, null, FALSE),
            createExample("생성뷰(무료) - 예시 프롬프트1", null, "FREE_ONE", TRUE), // 생성뷰(정방형) : 무료
            createExample("생성뷰(무료) - 예시 프롬프트2", null, "FREE_ONE", TRUE),
            createExample("생성뷰(무료) - 예시 프롬프트3", null, "FREE_ONE", TRUE),
            createExample("생성뷰(무료) - 예시 프롬프트4", null, "FREE_ONE", TRUE),
            createExample("생성뷰(무료) - 예시 프롬프트5", null, "FREE_ONE", TRUE),
            createExample("생성뷰(무료) - 예시 프롬프트6", null, "FREE_ONE", TRUE),
            createExample("생성뷰(무료) - 예시 프롬프트7", null, "FREE_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트1", null, "PAID_ONE", TRUE), // 생성뷰(정방형) : 유료
            createExample("생성뷰(유료-1인) - 예시 프롬프트2", null, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트3", null, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트4", null, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트5", null, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트6", null, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트7", null, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트1", null, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트2", null, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트3", null, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트4", null, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트5", null, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트6", null, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트7", null, "PAID_TWO", TRUE)
        );
    }



    @Test
    @DisplayName("피드뷰 결과 리스트 - 첫 번째 사진의 사진 비율이 RATIO_GARO인지 검증")
    void pictureRatioOfFirstResponseExampleIsRATIOGARO_And_sizeOfResultIs5_Then_Success() {
        //given
        given(responseExampleRepository.findAllByPromptOnlyIsFalse()).willReturn(mockResponseExamples.subList(0, 10));

        //when
        List<ExampleWithPictureFindResponseDto> result = responseExampleService.getAllResponseExamples();

        //then
        assertThat(result.get(0).getPicture().getPictureRatio()).isEqualTo(PictureRatio.RATIO_GARO);
    }

    @Test
    @DisplayName("피드뷰 결과 리스트 - RATIO_GARO가 포함된 예시 사진이 없을 때, 리스트는 그대로 반환")
    void noMatchingRatioGaro() {
        // given
        given(responseExampleRepository.findAllByPromptOnlyIsFalse()).willReturn(mockResponseExamples.subList(0, 8));

        // when
        List<ExampleWithPictureFindResponseDto> result = responseExampleService.getAllResponseExamples();

        // then
        assertThat(result.stream().
                    anyMatch(example -> PictureRatio.RATIO_GARO.equals(example.getPicture().getPictureRatio())))
                .isFalse();
    }

    @Test
    @DisplayName("피드뷰 결과 리스트 - 결과 리스트 - 랜덤으로 섞였는지 검증")
    void checkAllResponseExamplesIsShuffled() {
        // given
        given(responseExampleRepository.findAllByPromptOnlyIsFalse()).willReturn(mockResponseExamples.subList(0, 10));

        // when
        List<ExampleWithPictureFindResponseDto> result = responseExampleService.getAllResponseExamples();

        // then
        assertThat(result).isNotEqualTo(mockResponseExamples.subList(0, 7).stream()
                                            .map(ExampleWithPictureFindResponseDto::new)
                                            .collect(Collectors.toList())); // 우연히 같을 확률 = 1 / 5040
    }




    @Test
    @DisplayName("생성뷰(무료) 결과 리스트 - 크기가 5개인지 검증")
    void sizeOfResponseExampleInGenerateViewIs5_Then_Success() {
        //given
        String type = "FREE_ONE";
        given(responseExampleRepository.findAllByType(type)).willReturn(mockResponseExamples.subList(10, 17));

        //when
        List<ExampleWithSquarePicture> result_apiV1 = responseExampleService.getAllResponseExamplesInGenerateView();
        List<ExampleWithSquarePicture> result_apiV2 = responseExampleService.getAllResponseExamplesInGenerateViewV2(type);

        //then
        assertThat(result_apiV1).hasSize(5);
        assertThat(result_apiV2).hasSize(5);
    }

    @Test
    @DisplayName("생성뷰(무료) 결과 리스트 - 올바른 형식으로 변환되었는지 검증")
    void checkAllResponseExamplesInGenerateViewIsRightConversion() {
        // given
        String type = "FREE_ONE";
        String expectedUrl = "null/null"; // 수정 필요
        given(responseExampleRepository.findAllByType(type)).willReturn(mockResponseExamples.subList(10, 17));

        // when
        List<ExampleWithSquarePicture> result_apiV1 = responseExampleService.getAllResponseExamplesInGenerateView();
        List<ExampleWithSquarePicture> result_apiV2 = responseExampleService.getAllResponseExamplesInGenerateViewV2(type);

        // then
        result_apiV1.forEach(example -> {
            assertThat(example.getUrl()).isEqualTo(expectedUrl);
        });
        result_apiV2.forEach(example -> {
            assertThat(example.getUrl()).isEqualTo(expectedUrl);
        });
    }

    // shuffle 검증 관련 수정 필요, 어떻게 shuffle 검증??
    @Test
    @DisplayName("생성뷰(무료) 결과 리스트 - 랜덤으로 섞였는지 검증")
    void checkAllResponseExamplesInGenerateViewIsShuffled() {
        // given
        String type = "FREE_ONE";
        given(responseExampleRepository.findAllByType(type)).willReturn(mockResponseExamples.subList(10, 17));

        // when
        List<ExampleWithSquarePicture> result_firstRun_apiV1 = responseExampleService.getAllResponseExamplesInGenerateView();
        List<ExampleWithSquarePicture> result_secondRun_apiV1 = responseExampleService.getAllResponseExamplesInGenerateView();

        List<ExampleWithSquarePicture> result_firstRun_apiV2 = responseExampleService.getAllResponseExamplesInGenerateView();
        List<ExampleWithSquarePicture> result_secondRun_apiV2 = responseExampleService.getAllResponseExamplesInGenerateView();

        // then
        assertThat(result_firstRun_apiV1).isNotEqualTo(result_secondRun_apiV1); // 우연히 같을 확률 = 1 / 5040
        assertThat(result_firstRun_apiV2).isNotEqualTo(result_secondRun_apiV2); // 우연히 같을 확률 = 1 / 5040
    }




    @Test
    @DisplayName("생성뷰(유료) 결과 리스트 - 크기가 5개인지 검증")
    void sizeOfResponseExampleInPaidGenerateViewIs5_Then_Success() {
        //given
        String type1 = "PAID_ONE";
        given(responseExampleRepository.findAllByType(type1)).willReturn(mockResponseExamples.subList(17, 24));
        String type2 = "PAID_TWO";
        given(responseExampleRepository.findAllByType(type2)).willReturn(mockResponseExamples.subList(24, 31));

        //when
        List<ExampleWithSquarePicture> result_paidOne = responseExampleService.getAllResponseExamplesInGenerateViewV2(type1);
        List<ExampleWithSquarePicture> result_paidTwo = responseExampleService.getAllResponseExamplesInGenerateViewV2(type2);

        //then
        assertThat(result_paidOne).hasSize(5);
        assertThat(result_paidTwo).hasSize(5);
    }

    @Test
    @DisplayName("생성뷰(유료) 결과 리스트 - 올바른 형식으로 변환되었는지 검증")
    void checkAllResponseExamplesInPaidGenerateViewIsRightConversion() {
        // given
        String type1 = "PAID_ONE";
        given(responseExampleRepository.findAllByType(type1)).willReturn(mockResponseExamples.subList(17, 24));
        String type2 = "PAID_TWO";
        given(responseExampleRepository.findAllByType(type2)).willReturn(mockResponseExamples.subList(24, 31));
        String expectedUrl = "null/null"; // 수정 필요

        // when
        List<ExampleWithSquarePicture> result_paidOne = responseExampleService.getAllResponseExamplesInGenerateViewV2(type1);
        List<ExampleWithSquarePicture> result_paidTwo = responseExampleService.getAllResponseExamplesInGenerateViewV2(type2);

        // then
        result_paidOne.forEach(example -> {
            assertThat(example.getUrl()).isEqualTo(expectedUrl);
        });
        result_paidTwo.forEach(example -> {
            assertThat(example.getUrl()).isEqualTo(expectedUrl);
        });
    }

    // shuffle 검증 관련 수정 필요, 어떻게 shuffle 검증??
    @Test
    @DisplayName("생성뷰(유료) 결과 리스트 - 랜덤으로 섞였는지 검증")
    void checkAllResponseExamplesInPaidGenerateViewIsShuffled() {
        // given
        String type1 = "PAID_ONE";
        given(responseExampleRepository.findAllByType(type1)).willReturn(mockResponseExamples.subList(17, 24));
        String type2 = "PAID_TWO";
        given(responseExampleRepository.findAllByType(type2)).willReturn(mockResponseExamples.subList(24, 31));

        // when
        List<ExampleWithSquarePicture> result_firstRun_paidOne = responseExampleService.getAllResponseExamplesInGenerateViewV2(type1);
        List<ExampleWithSquarePicture> result_secondRun_paidOne = responseExampleService.getAllResponseExamplesInGenerateViewV2(type1);
        List<ExampleWithSquarePicture> result_firstRun_paidTwo = responseExampleService.getAllResponseExamplesInGenerateViewV2(type2);
        List<ExampleWithSquarePicture> result_secondRun_paidTwo = responseExampleService.getAllResponseExamplesInGenerateViewV2(type2);

        // then
        assertThat(result_firstRun_paidOne).isNotEqualTo(result_secondRun_paidOne); // 우연히 같을 확률 = 1 / 5040
        assertThat(result_firstRun_paidTwo).isNotEqualTo(result_secondRun_paidTwo); // 우연히 같을 확률 = 1 / 5040
    }





    private ResponseExample createExample(String prompt, PictureRatio ratio, String type, Boolean promptOnly) {
        return ResponseExample.builder()
                .prompt(prompt)
                .pictureRatio(ratio)
                .promptOnly(promptOnly)
                .type(type)
                .build();
    }


}
