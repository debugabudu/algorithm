-- 表： Weather
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | id            | int     |
-- | recordDate    | date    |
-- | temperature   | int     |
-- +---------------+---------+
-- id 是该表具有唯一值的列。
-- 没有具有相同 recordDate 的不同行。
-- 该表包含特定日期的温度信息
--
-- 编写解决方案，找出与之前（昨天的）日期相比温度更高的所有日期的 id 。
-- 返回结果 无顺序要求 。
SELECT w1.id AS id
FROM Weather w1
JOIN Weather w2
    ON DATEDIFF(w1.recordDate, w2.recordDate) = 1
WHERE w1.temperature > w2.temperature;