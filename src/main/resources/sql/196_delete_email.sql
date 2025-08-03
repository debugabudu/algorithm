-- 表: Person
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | id          | int     |
-- | email       | varchar |
-- +-------------+---------+
-- id 是该表的主键列(具有唯一值的列)。
-- 该表的每一行包含一封电子邮件。电子邮件将不包含大写字母。
--
-- 编写解决方案 删除 所有重复的电子邮件，只保留一个具有最小 id 的唯一电子邮件。
DELETE p1 FROM Person p1,
    Person p2
WHERE
    p1.Email = p2.Email AND p1.Id > p2.Id