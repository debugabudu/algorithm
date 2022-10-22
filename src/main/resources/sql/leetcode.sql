# 查询不订购的客户
select customers.name as customers
from customers
where customers.id not in(
    select customerId from orders
)

# 查询referee_id不为2的客户
select name from customer where referee_id<>2 or referee_id is null

# 筛选id为奇数且名字开头不为M的员工,计算其奖金为月薪,否则为0
select
    employee_id,
    case
        when employee_id % 2 = 1 and left(name, 1) <> 'M' then salary
        else 0
end as bonus
from Employees
order by employee_id asc;

# 交换性别(使用update且不产生中间表)
UPDATE salary
SET
    sex = CASE sex
              WHEN 'm' THEN 'f'
              ELSE 'm'
        END;

# 将表中的姓名全部变为第一个字母大些,其余小写
SELECT user_id, CONCAT(UPPER(LEFT(name, 1)), LOWER(SUBSTRING(name, 2))) AS name
FROM Users
ORDER BY user_id

# 按日期统计销售的商品
select
    sell_date,
    count(distinct product) as num_sold,
    group_concat(distinct product order by product asc) as products
from Activities
group by sell_date
order by sell_date asc;

# 查询第二高的工资
SELECT
    (SELECT DISTINCT
         Salary
     FROM
         Employee
     ORDER BY Salary DESC
        LIMIT 1 OFFSET 1) AS SecondHighestSalary;

# 销售人员表/公司表/订单表,查询未与red公司有交集的销售人员
select s.name from salesperson s
where s.sales_id not in (
    select o.sales_id from orders o left join company c on o.com_id=c.com_id where c.name='RED'
    )
