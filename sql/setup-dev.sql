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
       (11, 'url11', localtime, localtime),
       (12, 'url12', localtime, localtime),
       (13, 'url13', localtime, localtime),
       (14, 'url14', localtime, localtime),
       (15, 'url15', localtime, localtime),
       (16, 'url16', localtime, localtime),
       (17, 'url17', localtime, localtime),
       (18, 'url18', localtime, localtime),
       (19, 'url19', localtime, localtime),
       (20, 'url20', localtime, localtime),
       (21, 'url21', localtime, localtime),
       (22, 'url22', localtime, localtime),
       (23, 'url23', localtime, localtime),
       (24, 'url24', localtime, localtime),
       (25, 'url25', localtime, localtime),
       (26, 'url26', localtime, localtime),
       (27, 'url27', localtime, localtime),
       (28, 'url28', localtime, localtime),
       (29, 'url29', localtime, localtime),
       (30, 'url30', localtime, localtime);

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


insert ignore into post (id, user_id, main_picture_id, content, likes, post_status, created_at)
values (1, 3, 1, 'post content 테스트', 1001, 'POSTED', '20010101')
     , (2, 3, 3, 'post content 테스트2', 1002, 'POSTED', '20010102')
     , (3, 4, 4, 'deleted 테스트1', 1003, 'DELETED', '20010103')
     , (4, 3, 7, 'post content 테스트3', 1004, 'POSTED', '20010104')
     , (5, 3, 9, 'post content 테스트4', 5, 'POSTED', '20010105')
     , (6, 4, 12, 'post content 테스트5', 1006, 'POSTED', '20010106')
     , (7, 3, 13, 'post content 테스트6', 1007, 'POSTED', '20010107')
     , (8, 3, 15, 'post content 테스트7', 1008, 'POSTED', '20010108')
     , (9, 3, 16, 'post content 테스트8', 1009, 'POSTED', '20010109')
     , (10, 3, 17, 'post content 테스트9', 1010, 'POSTED', '20010110')
     , (11, 3, 21, 'post content 테스트10', 1011, 'POSTED', '2001011')
     , (12, 3, 22, 'post content 테스트11', 1012, 'POSTED', '20010112')
     , (13, 3, 27, 'post content 테스트12', 1013, 'POSTED', '20010113');


insert ignore into post_picture (id, picture_id, post_id, created_at)
values (1, 1, 1, '20020101'),
       (2, 2, 1, '20020102'),
       (3, 3, 2, '20020103'),
       (4, 4, 3, '20020104'),
       (5, 7, 4, '20020104'),
       (6, 8, 5, '20020101'),
       (7, 9, 5, '20020102'),
       (8, 10, 6, '20020103'),
       (9, 11, 6, '20020104'),
       (10, 12, 6, '20020104'),
       (11, 13, 7, '20020101'),
       (12, 14, 7, '20020102'),
       (13, 15, 8, '20020103'),
       (14, 16, 9, '20020104'),
       (15, 17, 10, '20020104'),
       (16, 18, 10, '20020101'),
       (17, 19, 10, '20020102'),
       (18, 20, 10, '20020103'),
       (19, 21, 11, '20020104'),
       (20, 22, 12, '20020104'),
       (21, 23, 13, '20020101'),
       (22, 24, 13, '20020102'),
       (23, 25, 13, '20020103'),
       (24, 26, 13, '20020104'),
       (25, 27, 13, '20020104');


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

