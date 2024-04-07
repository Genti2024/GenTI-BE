use genti;

set FOREIGN_KEY_CHECKS = 0;

truncate table user;
truncate table post;
truncate table picture_create_request;
truncate table picture_create_response;
truncate table post_like;
truncate table post_picture;
truncate table profile_picture;

set FOREIGN_KEY_CHECKS = 1;