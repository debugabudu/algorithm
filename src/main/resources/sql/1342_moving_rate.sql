-- 表：Movies
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | movie_id      | int     |
-- | title         | varchar |
-- +---------------+---------+
-- movie_id 是这个表的主键(具有唯一值的列)。
-- title 是电影的名字。
--
-- 表：Users
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | user_id       | int     |
-- | name          | varchar |
-- +---------------+---------+
-- user_id 是表的主键(具有唯一值的列)。
-- 'name' 列具有唯一值。
--
-- 表：MovieRating
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | movie_id      | int     |
-- | user_id       | int     |
-- | rating        | int     |
-- | created_at    | date    |
-- +---------------+---------+
-- (movie_id, user_id) 是这个表的主键(具有唯一值的列的组合)。
-- 这个表包含用户在其评论中对电影的评分 rating 。
-- created_at 是用户的点评日期。
--
-- 请你编写一个解决方案：
-- 查找评论电影数量最多的用户名。如果出现平局，返回字典序较小的用户名。
-- 查找在 February 2020 平均评分最高 的电影名称。如果出现平局，返回字典序较小的电影名称。
(select u.name results
 from MovieRating mr
          left join Users u using(user_id)
 group by u.user_id
 order by count(movie_id) desc, u.name asc
     limit 1)

union all

(select m.title results
 from MovieRating mr
          left join Movies m using(movie_id)
 where date_format(mr.created_at,'%Y-%m') = '2020-02'
 group by m.movie_id
 order by avg(rating) desc, m.title asc
     limit 1)