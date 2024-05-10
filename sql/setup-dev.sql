use genti;

set @adminId := 1;
set @userId := 2;
set @emptyUserId := 3;
set @creatorUserId := 4;
set @emptyCreatorId := 5;
set @oauthFirstJoinUserId := 6;
set @deactivatedUserId := 7;
set @deactivatedCreatorId := 8;

set @creatorId := 1;

insert ignore into user (id, created_at, modified_at, deleted_at, email, email_verified, introduction,
                         last_login_social_platform, login_id, nickname, password, roles, user_status,
                         username, profile_picture_id)
VALUES (:adminId, localtime, localtime, null, 'admin@gmail.com', true, null, 'GOOGLE', null, '어드민테스트1_닉네임', null,
        'ROLE_ADMIN,ROLE_MANAGER,ROLE_CREATOR,ROLE_USER',
        'ACTIVATED', '어드민이름', null),
       (:userId, localtime, localtime, null, 'user@gmail.com', true, '유저_소개1', 'GOOGLE', null, '유저테스트1_닉네임', null,
        'ROLE_USER',
        'ACTIVATED', '유저이름1', null),
       (:emptyUserId, localtime, localtime, null, 'emptyUser@gmail.com', true, '유저_소개2', 'GOOGLE', null, '유저테스트1_닉네임',
        null,
        'ROLE_USER', 'ACTIVATED', '유저이름2', null),
       (:creatorUserId, localtime, localtime, null, 'creator@gmail.com', true, '공급자_소개', 'GOOGLE', null, '유저테스트2_닉네임',
        null,
        'ROLE_CREATOR', 'ACTIVATED', '공급자이름1', null),
       (:emptyCreatorId, localtime, localtime, null, 'emptyCreator@gmail.com', true, '공급자_소개2', 'GOOGLE', null,
        '공급자테스트2_닉네임', null, 'ROLE_CREATOR', 'ACTIVATED', '공급자이름2', null),
       (:oauthFirstJoinId, localtime, localtime, null, 'oauthFirstJoin@gmail.com', true, '최초가입자_소개', 'GOOGLE', null,
        '최초가입자_닉네임', null, 'ROLE_OAUTH_FIRST_JOIN,ROLE_USER', 'ACTIVATED', '최초가입자이름', null),
       (:deactivatedUserId, localtime, localtime, null, 'deactivatedUser@gmail.com', true, '최초가입자_소개', 'GOOGLE', null,
        '비활성화된유저_닉네임', null, 'ROLE_USER', 'DEACTIVATED', '비활성화된유저이름', null),
       (:deactivatedCreatorId, localtime, localtime, null, 'deactivatedCreator@gmail.com', true, '최초가입자_소개', 'GOOGLE',
        null,
        '비활성화된공급자_닉네임', null, 'ROLE_CREATOR', 'DEACTIVATED', '비활성화된공급자이름', null);

insert ignore into creator (id, user_id, workable, created_at, modified_at)
values (:creatorId, :creatorUserId, true, localtime, localtime),
       (2, :emptyCreatorId, false, localtime, localtime);

insert ignore into picture_pose (id, created_at, modified_at, url)
values (1, LOCALTIME, LOCALTIME, 'pose_picture_url1'),
       (2, LOCALTIME, LOCALTIME, 'pose_picture_url2'),
       (3, LOCALTIME, LOCALTIME, 'pose_picture_url3'),
       (4, LOCALTIME, LOCALTIME, 'pose_picture_url4');

insert ignore into picture_user_face (id, created_at, modified_at, url, user_id)
values (1, LOCALTIME, LOCALTIME, 'user_face_picture_url1', :userId),
       (2, LOCALTIME, LOCALTIME, 'user_face_picture_url2', :userId),
       (3, LOCALTIME, LOCALTIME, 'user_face_picture_url3', :userId),
       (4, LOCALTIME, LOCALTIME, 'user_face_picture_url3', :userId),
       (5, LOCALTIME, LOCALTIME, 'user_face_picture_url3', :userId),
       (6, LOCALTIME, LOCALTIME, 'user_face_picture_url3', :userId),
       (7, LOCALTIME, LOCALTIME, 'user_face_picture_url3', :userId),
       (8, LOCALTIME, LOCALTIME, 'user_face_picture_url3', :userId),
       (9, LOCALTIME, LOCALTIME, 'user_face_picture_url3', :userId);



insert ignore into picture_generate_request (id, creator_id, picture_pose_id, requester_id, prompt, camera_angle,
                                             request_status, shot_coverage, created_at, modified_at)
VALUES (1, null, 4, :userId, 'prompt_test_1', '위에서 촬영', 'BEFORE_WORK', '얼굴만 클로즈업', '2000-01-01 07:00:00', localtime),
       (2, null, 3, :userId, 'prompt_test_2', '같은 높이에서 촬영', 'CANCELED', '허리 위로 촬영', '2020-01-01 07:00:00',
        localtime),
       (3, :creatorId, 2, :userId, 'prompt_test_3', '아래에서 촬영', 'IN_PROGRESS', '무릎 위로 촬영', '2000-01-01 07:00:00',
        localtime),
       (4, :creatorId, 2, :userId, 'prompt_test_4', '아래에서 촬영', 'IN_PROGRESS', '무릎 위로 촬영', '2000-01-01 07:00:00',
        localtime),
       (5, :creatorId, 2, :userId, 'prompt_test_5', '아래에서 촬영', 'IN_PROGRESS', '무릎 위로 촬영', '2000-01-01 07:00:00',
        localtime),
       (6, :creatorId, 2, :userId, 'prompt_test_6', '아래에서 촬영', 'REPORTED', '무릎 위로 촬영', '2000-01-01 07:00:00',
        localtime),
       (7, :creatorId, 1, :userId, 'prompt_test_7', '위에서 촬영', 'COMPLETED', '전신 촬영', '2020-01-01 07:00:00', localtime),
       (8, :creatorId, 1, :userId, 'prompt_test_7', '위에서 촬영', 'REPORTED', '전신 촬영', '2020-01-01 07:00:00', localtime);

insert ignore into picture_generate_request_picture_user_face (picture_generate_request_id, user_face_picture_list_id)

values (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (4, 3),
       (5, 4),
       (5, 5),
       (5, 6),
       (6, 4),
       (6, 5),
       (6, 6),
       (7, 4),
       (7, 5),
       (7, 6),
       (8, 7),
       (8, 8),
       (8, 9);



insert ignore into picture_generate_response (id, created_at, modified_at, status, creator_id, request_id)
values (1, localtime, localtime, 'BEFORE_WORK', :creatorId, 3),
       (2, localtime, localtime, 'SUBMITTED_FIRST', :creatorId, 4),
       (3, localtime, localtime, 'SUBMITTED_FINAL', :creatorId, 5),
       (4, localtime, localtime, 'REPORTED', :creatorId, 6),
       (5, localtime, localtime, 'COMPLETED', :creatorId, 7),
       (6, localtime, localtime, 'REPORTED', :creatorId, 8);

insert ignore into report (id, created_at, modified_at, content, report_status, picture_generate_response_id)
values (1, localtime, localtime, '변태자식이 본인 발가락 사진을 보낸 것 같습니다.', 'NOT_RESOLVED', 4),
       (2, localtime, localtime, '모르는 사람 얼굴이에요', 'RESOLVED', 6);

insert ignore into picture_created_by_creator (id, created_at, modified_at, url, picture_generate_response_id)
values (1, localtime, localtime, '얼굴 완성 전 url 1', 2),
       (2, localtime, localtime, '얼굴 완성 전 url 2', 3),
       (3, localtime, localtime, '얼굴 완성 전 url 3', 4);

insert ignore into picture_completed (id, created_at, modified_at, url, picture_generate_response_id, user_id)
values (1, localtime, localtime, '얼굴 완성 사진 url 1', 3, :userId),
       (2, localtime, localtime, '얼굴 완성 사진 url 2', 4, :userId),
       (3, localtime, localtime, '얼굴 완성 사진 url 3', 5, :userId),
       (4, localtime, localtime, '얼굴 완성 사진 url 4', 6, :userId);


# insert ignore into post (id, user_id, main_picture_id, content, likes, post_status, created_at)
# values (1, 3, 1, 'post content 테스트', 1001, 'POSTED', '20010101')
#      , (2, 3, 3, 'post content 테스트2', 1002, 'POSTED', '20010102')
#      , (3, 4, 4, 'deleted 테스트1', 1003, 'DELETED', '20010103')
#      , (4, 3, 7, 'post content 테스트3', 1004, 'POSTED', '20010104')
#      , (5, 3, 9, 'post content 테스트4', 5, 'POSTED', '20010105')
#      , (6, 4, 12, 'post content 테스트5', 1006, 'POSTED', '20010106')
#      , (7, 3, 13, 'post content 테스트6', 1007, 'POSTED', '20010107')
#      , (8, 3, 15, 'post content 테스트7', 1008, 'POSTED', '20010108')
#      , (9, 3, 16, 'post content 테스트8', 1009, 'POSTED', '20010109')
#      , (10, 3, 17, 'post content 테스트9', 1010, 'POSTED', '20010110')
#      , (11, 3, 21, 'post content 테스트10', 1011, 'POSTED', '2001011')
#      , (12, 3, 22, 'post content 테스트11', 1012, 'POSTED', '20010112')
#      , (13, 3, 27, 'post content 테스트12', 1013, 'POSTED', '20010113');


# insert ignore into post_picture (id, picture_id, post_id, created_at)
# values (1, 1, 1, '20020101'),
#        (2, 2, 1, '20020102'),
#        (3, 3, 2, '20020103'),
#        (4, 4, 3, '20020104'),
#        (5, 7, 4, '20020104'),
#        (6, 8, 5, '20020101'),
#        (7, 9, 5, '20020102'),
#        (8, 10, 6, '20020103'),
#        (9, 11, 6, '20020104'),
#        (10, 12, 6, '20020104'),
#        (11, 13, 7, '20020101'),
#        (12, 14, 7, '20020102'),
#        (13, 15, 8, '20020103'),
#        (14, 16, 9, '20020104'),
#        (15, 17, 10, '20020104'),
#        (16, 18, 10, '20020101'),
#        (17, 19, 10, '20020102'),
#        (18, 20, 10, '20020103'),
#        (19, 21, 11, '20020104'),
#        (20, 22, 12, '20020104'),
#        (21, 23, 13, '20020101'),
#        (22, 24, 13, '20020102'),
#        (23, 25, 13, '20020103'),
#        (24, 26, 13, '20020104'),
#        (25, 27, 13, '20020104');

#
# insert ignore into post_like (id, post_id, user_id)
# values (1, 1, 3),
#        (2, 2, 3),
#        (3, 2, 3),
#        (4, 3, 4),
#        (5, 4, 4);

#
# insert ignore into picture_profile (id, created_at, modified_at, url)
# values (1, localtime, localtime, 'profile_url1'),
#        (2, localtime, localtime, 'profile_url2');
