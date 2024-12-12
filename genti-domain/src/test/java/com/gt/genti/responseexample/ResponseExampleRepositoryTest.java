package com.gt.genti.responseexample;


import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.responseexample.model.ResponseExample;
import com.gt.genti.picture.responseexample.repository.ResponseExampleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ResponseExampleRepositoryTest {

    @Autowired
    ResponseExampleRepository responseExampleRepository;

    private List<ResponseExample> mockResponseExamples;

//    @Autowired
//    private ApplicationContext context;

//    @Test
//    @DisplayName("등록된 빈 확인")
//    void printLoadedBeans() {
//        Arrays.stream(context.getBeanDefinitionNames()).sorted().forEach(System.out::println);
//    }

    @BeforeEach
    void setUp() {
        mockResponseExamples = List.of(  // Service 계층과 동일
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
            createExample("생성뷰(무료) - 예시 프롬프트1", PictureRatio.NONE, "FREE_ONE", TRUE), // 생성뷰(정방형) : 무료
            createExample("생성뷰(무료) - 예시 프롬프트2", PictureRatio.NONE, "FREE_ONE", TRUE),
            createExample("생성뷰(무료) - 예시 프롬프트3", PictureRatio.NONE, "FREE_ONE", TRUE),
            createExample("생성뷰(무료) - 예시 프롬프트4", PictureRatio.NONE, "FREE_ONE", TRUE),
            createExample("생성뷰(무료) - 예시 프롬프트5", PictureRatio.NONE, "FREE_ONE", TRUE),
            createExample("생성뷰(무료) - 예시 프롬프트6", PictureRatio.NONE, "FREE_ONE", TRUE),
            createExample("생성뷰(무료) - 예시 프롬프트7", PictureRatio.NONE, "FREE_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트1", PictureRatio.NONE, "PAID_ONE", TRUE), // 생성뷰(정방형) : 유료
            createExample("생성뷰(유료-1인) - 예시 프롬프트2", PictureRatio.NONE, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트3", PictureRatio.NONE, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트4", PictureRatio.NONE, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트5", PictureRatio.NONE, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트6", PictureRatio.NONE, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-1인) - 예시 프롬프트7", PictureRatio.NONE, "PAID_ONE", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트1", PictureRatio.NONE, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트2", PictureRatio.NONE, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트3", PictureRatio.NONE, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트4", PictureRatio.NONE, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트5", PictureRatio.NONE, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트6", PictureRatio.NONE, "PAID_TWO", TRUE),
            createExample("생성뷰(유료-2인) - 예시 프롬프트7", PictureRatio.NONE, "PAID_TWO", TRUE)
        );
        responseExampleRepository.saveAll(mockResponseExamples);
    }

    @Test
    @DisplayName("피드뷰 - 모든 예시 사진이 promptOnly == false && type == null && pictureRatio != NONE 인지 검증")
    void findAllFeedViewMeetsAllConditions_Then_Success() {
        //when
        List<ResponseExample> result = responseExampleRepository.findAllByPromptOnlyIsFalse();

        //then
        assertThat(result)
                .isNotEmpty()
                .allMatch(example -> !example.getPromptOnly())
                .allMatch(example -> example.getType() == null)
                .allMatch(example ->
                    example.getPictureRatio() == PictureRatio.RATIO_GARO ||
                    example.getPictureRatio() == PictureRatio.RATIO_SERO
                );
    }

    @Test
    @DisplayName("피드뷰 - 조건에 맞는 예시가 없을 때 빈 리스트가 반환되는지 검증")
    void findAllFeedViewWhenNoMatchingData_Then_EmptyListReturned() {
        //given
        responseExampleRepository.deleteAll();
        responseExampleRepository.saveAll(mockResponseExamples.subList(10, mockResponseExamples.size()));

        //when
        List<ResponseExample> result = responseExampleRepository.findAllByPromptOnlyIsFalse();

        //then
        assertThat(result).isEmpty();
    }




    private ResponseExample createExample(String prompt, PictureRatio ratio, String type, Boolean promptOnly) {
        return ResponseExample.builder()
                .key("/ADMIN_UPLOAD/test.png")
                .prompt(prompt)
                .pictureRatio(ratio)
                .promptOnly(promptOnly)
                .type(type)
                .build();
    }


}
