package com.gt.genti.picturegeneraterequest.command;

import com.gt.genti.picture.PictureRatio;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdvancedPGREQSaveCommand extends PGREQSaveCommand {

    List<String> otherFacePictureKeyList;

    @Builder(builderMethodName = "advancedBuilder")
    public AdvancedPGREQSaveCommand(String prompt,
                                    List<String> facePictureKeyList,
                                    List<String> otherFacePictureKeyList,
                                    PictureRatio pictureRatio) {
        this.prompt = prompt;
        this.facePictureKeyList = facePictureKeyList;
        this.otherFacePictureKeyList = otherFacePictureKeyList;
        this.pictureRatio = pictureRatio;
    }
}
