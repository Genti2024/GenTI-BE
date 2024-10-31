package com.gt.genti.picturegenerateresponse.controller;

import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picturegenerateresponse.service.PictureGenerateWorkService;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.user.model.AuthUser;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile({"staging", "local"})
@RestController
@RequestMapping("/api/v1/frontend/picture-generate-responses")
@RequiredArgsConstructor
public class FrontendPGRESController {

    private final PictureGenerateWorkService pictureGenerateWorkService;

    @PostMapping
    ResponseEntity<GentiResponse.ApiResult<Boolean>> finishPGRESByFrontend(
        @AuthUser Long userId
    ){
        return GentiResponse.success(pictureGenerateWorkService.finishPGRESByFrontend(userId));
    }

}
