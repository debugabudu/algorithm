--查询不订购的客户
select customers.name as customers
from customers
where customers.id not in(
    select customerId from orders
)

--查询referee_id不为2的客户
select name from customer where referee_id<>2 or referee_id is null

--筛选id为奇数且名字开头不为M的员工,计算其奖金为月薪,否则为0
select
    employee_id,
    case
        when employee_id % 2 = 1 and left(name, 1) <> 'M' then salary
        else 0
end as bonus
from Employees
order by employee_id asc;

--交换性别(使用update且不产生中间表)
UPDATE salary
SET
    sex = CASE sex
              WHEN 'm' THEN 'f'
              ELSE 'm'
        END;

--将表中的姓名全部变为第一个字母大些,其余小写
SELECT user_id, CONCAT(UPPER(LEFT(name, 1)), LOWER(SUBSTRING(name, 2))) AS name
FROM Users
ORDER BY user_id

--按日期统计销售的商品
select
    sell_date,
    count(distinct product) as num_sold,
    group_concat(distinct product order by product asc) as products
from Activities
group by sell_date
order by sell_date asc;

--查询第二高的工资
SELECT
    (SELECT DISTINCT
         Salary
     FROM
         Employee
     ORDER BY Salary DESC
        LIMIT 1 OFFSET 1) AS SecondHighestSalary;

--销售人员表/公司表/订单表,查询未与red公司有交集的销售人员
select s.name from salesperson s
where s.sales_id not in (
    select o.sales_id from orders o left join company c on o.com_id=c.com_id where c.name='RED'
    )

--order by 对多列排序的时候，先排序的列放前面，后排序的列放后面。并且，不同的列可以有不同的排序规则。

--返回每个订单号（order_num）各有多少行数（order_lines），并按 order_lines 对结果进行升序排序。
select order_num, count(order_num) as order_lines
from OrderItems
group by order_num
order by order_lines;

--返回订单数量总和不小于 100 的所有订单号，最后结果按照订单号升序排序。
--直接聚合
select order_num
from OrderItems
group by order_num
having sum(quantity) >= 100
order by order_num;
--子查询
select order_num
from (select order_num, sum(quantity) as sum_num
      from OrderItems group by order_num having sum_num >= 100
     ) a
order by order_num;

--使用子查询来确定哪些订单（在 OrderItems 中）购买了 prod_id 为 "BR01" 的产品，
-- 然后从 Orders 表中返回每个产品对应的顾客 ID（cust_id）和订单日期（order_date），按订购日期对结果进行升序排序。
--写法 1：子查询
select cust_id, order_date
from Orders
where order_num in (
    select order_num
    from OrderItems
    where prod_id = 'BR01'
)
order by order_date;
--写法 2: 连接表
select
    b.cust_id, b.order_date
from
    OrderItems a, Orders b
where
    a.order_num = b.order_num
    and a.prod_id = 'BR01'
order by
    order_date;

--返回购买 prod_id 为 BR01 的产品的所有顾客的电子邮件（Customers 表中的 cust_email），结果无需排序。
--写法 1：子查询
select cust_email
from Customers
where cust_id in (
    select cust_id
    from Orders
    where order_num in (
        select order_num
        from OrderItems
        where prod_id = 'BR01'
    )
);
--写法 2: 连接表（inner join）
select c.cust_email
from OrderItems a, Orders b, Customers c
where a.order_num = b.order_num
  and b.cust_id = c.cust_id
  and a.prod_id = 'BR01';
--写法 3：连接表（left join）
select
    c.cust_email
from
    Orders a
    left join OrderItems b on a.order_num = b.order_num
    left join Customers c on a.cust_id = c.cust_id
where
    b.prod_id = 'BR01';

