select
    p1_0.id,
    p1_0.camera_angle,
    p1_0.created_at,
    p1_0.creator_id,
    p1_0.modified_at,
    p1_0.pose_picture_id,
    p1_0.prompt,
    p1_0.request_status,
    p1_0.requester_id,
    p1_0.shot_coverage
from
    picture_generate_request p1_0
where
    p1_0.requester_id=?
  and p1_0.request_status in('BEFORE_WORK','IN_PROGRESS')
order by
    p1_0.created_at desc