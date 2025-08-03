-- 表：Employees
-- +-------------+----------+
-- | Column Name | Type     |
-- +-------------+----------+
-- | employee_id | int      |
-- | name        | varchar  |
-- | reports_to  | int      |
-- | age         | int      |
-- +-------------+----------+
-- employee_id 是这个表中具有不同值的列。
-- 该表包含员工以及需要听取他们汇报的上级经理的 ID 的信息。 有些员工不需要向任何人汇报（reports_to 为空）。
--
-- 对于此问题，我们将至少有一个其他员工需要向他汇报的员工，视为一个经理。
-- 编写一个解决方案来返回需要听取汇报的所有经理的 ID、名称、直接向该经理汇报的员工人数，以及这些员工的平均年龄，其中该平均年龄需要四舍五入到最接近的整数。
-- 返回的结果集需要按照 employee_id 进行排序。
select m.employee_id,
       m.name,
       count(*) as reports_count,
       round(avg(e.age),0) as average_age
from Employees e
         join Employees m
              on e.reports_to = m.employee_id
group by m.employee_id
order by employee_id