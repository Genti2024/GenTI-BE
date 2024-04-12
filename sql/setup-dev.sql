use genti;

insert ignore into picture (id, url)
values (1, 'url1'),
       (2, 'url2'),
       (3, 'url3'),
       (4, 'url4'),
       (5, 'url5'),
       (6, 'url6'),
       (7, 'url7'),
       (8, 'url8'),
       (9, 'url9'),
       (10, 'url10'),
       (11, 'url11');

insert ignore into profile_picture (id, picture_id, created_at)
values (1, 5, '20030101'),
       (2, 6, '20030102');

insert ignore into user (id, profile_picture_id, email, introduction, username, oauth_picture_url, user_role)
values (1, 1, 'devbrseo@gmail.com', '어드민테스트1_소개', '어드민테스트1_이름', null, 'ADMIN'),
       (2, 2, 'devbrseo@gmail.com', '유저테스트1_소개', '유저테스트1_이름', null, 'USER');

insert ignore into post (id, user_id, content, likes, post_status, created_at)
values (1, 1, 'post content 테스트', 1001, 'POSTED', '20010101')
     , (2, 1, 'post content 테스트2', 1002, 'POSTED', '20010102')
     , (3, 1, 'deleted 테스트1', 1003, 'DELETED', '20010103')
     , (4, 2, '테스트1', 1004, 'POSTED', '20010104');


insert ignore into post_picture (id, picture_id, post_id, created_at)
values (1, 1, 1, '20020101'),
       (2, 2, 1, '20020102'),
       (3, 3, 1, '20020103'),
       (4, 4, 1, '20020104');


insert ignore into post_like (id, post_id, user_id)
values (1, 1, 1),
       (2, 2, 1),
       (3, 2, 2),
       (4, 4, 2);

