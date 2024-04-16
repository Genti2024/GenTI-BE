use genti;

insert ignore into picture (id, url, created_at, modified_at)
values (1, 'url1', localtime, localtime),
       (2, 'url2', localtime, localtime),
       (3, 'url3', localtime, localtime),
       (4, 'url4', localtime, localtime),
       (5, 'url5', localtime, localtime),
       (6, 'url6', localtime, localtime),
       (7, 'url7', localtime, localtime),
       (8, 'url8', localtime, localtime),
       (9, 'url9', localtime, localtime),
       (10, 'url10', localtime, localtime),
       (11, 'url11', localtime, localtime);

insert ignore into profile_picture (id, picture_id, created_at, modified_at)
values (1, 5, '20030101', localtime),
       (2, 6, '20030102', localtime);

insert ignore into user (id, profile_picture_id, oauth_picture_url, email, introduction, username, user_role,
                         user_status, creator_id, created_at, modified_at)
values (1, null, null, 'admin@gmail.com', null, '어드민테스트1_이름', 'ADMIN', 'ACTIVATED', null, LOCALTIME, LOCALTIME),
       (2, null, null, 'creator@gmail.com', null, '유저테스트1_이름', 'USER', 'ACTIVATED', 1, LOCALTIME, LOCALTIME),
       (3, 1, 'oauth_picture_url_user_1', 'user1@gmail.com', '유저테스트1_소개', '유저테스트1_이름', 'USER', 'ACTIVATED', null,
        LOCALTIME, LOCALTIME),
       (4, 2, 'oauth_picture_url_user_2', 'user2@gmail.com', '유저테스트2_소개', '유저테스트2_이름', 'USER', 'ACTIVATED', null,
        LOCALTIME, LOCALTIME);


insert ignore into post (id, user_id, content, likes, post_status, created_at)
values (1, 3, 'post content 테스트', 1001, 'POSTED', '20010101')
     , (2, 3, 'post content 테스트2', 1002, 'POSTED', '20010102')
     , (3, 4, 'deleted 테스트1', 1003, 'DELETED', '20010103')
     , (4, 5, '테스트1', 1004, 'POSTED', '20010104');


insert ignore into post_picture (id, picture_id, post_id, created_at)
values (1, 1, 1, '20020101'),
       (2, 2, 1, '20020102'),
       (3, 3, 2, '20020103'),
       (4, 4, 3, '20020104'),
       (5, 5, 4, '20020104');


insert ignore into post_like (id, post_id, user_id)
values (1, 1, 3),
       (2, 2, 3),
       (3, 2, 3),
       (4, 3, 4),
       (5, 4, 4);

insert ignore into pose_picture (id, url, created_at, modified_at)
values (1, 'pose_picture_url1', LOCALTIME, LOCALTIME),
       (2, 'pose_picture_url2', LOCALTIME, LOCALTIME),
       (3, 'pose_picture_url3', LOCALTIME, LOCALTIME),
       (4, 'pose_picture_url4', LOCALTIME, LOCALTIME);

insert ignore into user_face_picture (id, url, user_id, created_at, modified_at)
values (1, 'user_face_picture_url1', 3, LOCALTIME, LOCALTIME),
       (2, 'user_face_picture_url2', 3, LOCALTIME, LOCALTIME),
       (3, 'user_face_picture_url3', 3, LOCALTIME, LOCALTIME);

insert ignore into creator (id, workable, created_at, modified_at)
values (1, true, localtime, localtime);

insert ignore into picture_generate_request (id, creator_id, pose_picture_id, requester_id, prompt, camera_angle,
                                             request_status, shot_coverage, created_at, modified_at)
VALUES (1, 1, 4, 3, 'prompt_test_1', '위에서 촬영', 'BEFORE_WORK', '얼굴만 클로즈업', '2000-01-01 07:00:00', localtime),
       (2, 1, 3, 3, 'prompt_test_2', '같은 높이에서 촬영', 'IN_PROGRESS', '허리 위로 촬영', '2020-01-01 07:00:00', localtime),
       (3, 1, 2, 4, 'prompt_test_3', '아래에서 촬영', 'COMPLETED', '무릎 위로 촬영', '2000-01-01 07:00:00', localtime),
       (4, 1, 1, 4, 'prompt_test_4', '위에서 촬영', 'CANCELED', '전신 촬영', '2020-01-01 07:00:00', localtime);

