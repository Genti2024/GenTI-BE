use genti;

insert ignore into user (id, profile_picture_id, email, introduction, username, oauth_picture_url, user_role)
values (1, null, 'devbrseo@gmail.com', '어드민테스트1', null, null, 'ADMIN'),
       (2, null, 'devbrseo@gmail.com', '유저테스트1', null, null, 'USER');

insert ignore into post (id, user_id, content, likes, post_status, created_at)
values (1, 1, 'post content 테스트', 1001, 'POSTED', '20010101')
     , (2, 1, 'post content 테스트2', 1002, 'POSTED', '20010102')
     , (3, 1, 'deleted 테스트1', 1003, 'DELETED', '20010103')
     , (4, 2, '테스트1', 1004, 'POSTED', '20010104');

insert ignore into post_like (id, post_id, user_id)
values (1, 1, 1),
       (2, 2, 1),
       (3, 2, 2),
       (4, 4, 2);

insert ignore into picture (id)
insert ignore into post_picture (id, picture_id, post_id, created_at)
values (1, 1, 1, '20020101'),
       (2, 1, '20020101'),
       (3, 'post_picture 테스트 url1', 1, '20020101'),
       (4, 'post_picture 테스트 url1', 1, '20020101');

insert ignore into profile_picture (id,)
