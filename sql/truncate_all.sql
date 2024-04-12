use genti;

set FOREIGN_KEY_CHECKS = 0;

truncate table picture;
truncate table picture_create_request;
truncate table picture_create_request_face_picture_list;
truncate table picture_create_response;
truncate table post;
truncate table post_like;
truncate table post_picture;
truncate table profile_picture;
truncate table user;

set FOREIGN_KEY_CHECKS = 1;