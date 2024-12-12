package com.gt.genti.picture.userverification.model;

import com.gt.genti.common.picture.model.PictureEntity;
import com.gt.genti.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "picture_user_verification")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureUserVerification extends PictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Builder
    public PictureUserVerification(Long id, String key, User user) {
        this.id = id;
        this.key = key;
        this.setUploadedBy(user);
    }

}
