use genti;

select p.id,
       u.id,
       (select pic.url
        from pictureCreated pic
        where pic.id = profp.picture_id),
       (select pic.url
        from pictureCreated pic
                 inner join post_picture postp
        where pic.id = postp.picture_id),
       p.content,
       p.likes,
       p.created_at
from post p
         inner join user u
         inner join profile_picture profp
where p.user_id = u.id
  and u.profile_picture_id = profp.id
  and p.id between :startId and :endId
  and p.post_status != 'DELETED'
order by p.id desc;