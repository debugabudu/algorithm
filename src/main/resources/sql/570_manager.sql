-- 表: Employee
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | id          | int     |
-- | name        | varchar |
-- | department  | varchar |
-- | managerId   | int     |
-- +-------------+---------+
-- id 是此表的主键（具有唯一值的列）。
-- 该表的每一行表示雇员的名字、他们的部门和他们的经理的id。
-- 如果managerId为空，则该员工没有经理。
-- 没有员工会成为自己的管理者。
--
-- 编写一个解决方案，找出至少有五个直接下属的经理。
-- 只返回name即可，以 任意顺序 返回结果表。
select name
from (
         select m.name as name, count(e.Id) as cnt
         from
             Employee as m join Employee as e
                                on m.Id = e.ManagerId
         group by m.Id
     ) r
where cnt >= 5