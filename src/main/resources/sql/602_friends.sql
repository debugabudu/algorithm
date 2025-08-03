-- RequestAccepted 表：
-- +----------------+---------+
-- | Column Name    | Type    |
-- +----------------+---------+
-- | requester_id   | int     |
-- | accepter_id    | int     |
-- | accept_date    | date    |
-- +----------------+---------+
-- (requester_id, accepter_id) 是这张表的主键(具有唯一值的列的组合)。
-- 这张表包含发送好友请求的人的 ID ，接收好友请求的人的 ID ，以及好友请求通过的日期。
--
-- 编写解决方案，找出拥有最多的好友的人和他拥有的好友数目。
-- 生成的测试用例保证拥有最多好友数目的只有 1 个人。
select t1.ids as id,count(*) as num
from(
        select requester_id as ids from RequestAccepted
        union all
        select accepter_id as ids from RequestAccepted
    ) as t1
group by id
order by num desc
    limit 1;