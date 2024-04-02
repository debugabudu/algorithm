-- select (distinct) 列名
-- from 表名
-- where 选择条件（比较运算，逻辑运算，in、like、between-and、is null）
-- group by 分组标志
-- having 针对分组的选择条件
-- order by 排序规则

-- 1.进店却未进行交易的顾客
-- Visits-visit_id和customer_id，Transactions-transaction_id、visit_id和amount
-- 给出顾客id和只光顾不交易的次数，任意顺序返回
select v.customer_id as customer_id, count(v.customer_id) as count_no_trans
from Visits v left join Transactions t
                        on v.visit_id = t.visit_id
where transaction_id is null
group by customer_id

-- 2.找出比前一天温度更高的所有日期id，任意顺序返回
-- Weather-id、recordDate、temperature
select w1.id as id
from Weather w1 join Weather w2
on DATEDIFF(w1.recordDate, w2.recordDate) = 1 and w1.temperature > w2.temperature

-- 3.计算每个机器的平均运行时间（保留三位小数）
-- Activity-machine_id、process_id、activity_type(start/end)、timestamp
select a1.machine_id as machine_id, round(avg(a1.timestamp - a2.timestamp), 3) as processing_time
from Activity a1, Activity a2
where a1.machine_id = a2.machine_id
  and a1.process_id = a2.process_id
  and a1.activity_type = 'end'
  and a2.activity_type = 'start'
group by machine_id

-- 4.学生们参加各科考试的次数
-- Students-id、mame，Subjects-name，Examinations-stu_id、sub_name
SELECT
    s.student_id, s.student_name, sub.subject_name, IFNULL(grouped.attended_exams, 0) AS attended_exams
FROM
    Students s
        CROSS JOIN
    Subjects sub
        LEFT JOIN (
        SELECT student_id, subject_name, COUNT(*) AS attended_exams
        FROM Examinations
        GROUP BY student_id, subject_name
    ) grouped
        ON s.student_id = grouped.student_id AND sub.subject_name = grouped.subject_name
ORDER BY s.student_id, sub.subject_name;

-- 5.计算每个用户的确认率，保留2位小数
-- Signups-user_id、time，Confirmations-user_id、time、action（confirmed、timeout）
SELECT
    s.user_id,
    ROUND(IFNULL(AVG(c.action='confirmed'), 0), 2) AS confirmation_rate
FROM
    Signups AS s
        LEFT JOIN
    Confirmations AS c
    ON
        s.user_id = c.user_id
GROUP BY
    s.user_id

-- 6.计算产品的平均售价（保留2位小数）
-- Prices-product_id、start、end、price，UnitsSold-product_id、date、units
SELECT
    product_id,
    IFNULL(Round(SUM(sales) / SUM(units), 2), 0) AS average_price
FROM (
         SELECT
             Prices.product_id AS product_id,
             Prices.price * UnitsSold.units AS sales,
             UnitsSold.units AS units
         FROM Prices
                  LEFT JOIN UnitsSold ON Prices.product_id = UnitsSold.product_id
             AND (UnitsSold.purchase_date BETWEEN Prices.start_date AND Prices.end_date)
     ) T
GROUP BY product_id

-- 7.计算每个月、每个国家的事务总数、已批准事务数和总金额
-- Transactions-id、country、state（approved、declined）、amount、date
SELECT DATE_FORMAT(trans_date, '%Y-%m') AS month,
    country,
    COUNT(*) AS trans_count,
    COUNT(IF(state = 'approved', 1, NULL)) AS approved_count,
    SUM(amount) AS trans_total_amount,
    SUM(IF(state = 'approved', amount, 0)) AS approved_total_amount
FROM Transactions
GROUP BY month, country

-- 8.计算即时订单（下单和预期送达日期相同）在客户首次订单中的比例，保留2位小数
-- Delivery-delivery_id、customer_id、order_date、pref_date
select round (
               sum(order_date = customer_pref_delivery_date) * 100 /
               count(*),
               2
       ) as immediate_percentage
from Delivery
where (customer_id, order_date) in (
    select customer_id, min(order_date)
    from delivery
    group by customer_id
)

-- 9.查询所有的经理id、名称、直接向其汇报的员工人数和平均年龄（年龄四舍五入到整数）
-- Employees-id、name、report_to、age
select m.employee_id, m.name,
       count(*) as reports_count,
       round(avg(e.age),0) as average_age
from Employees e
         join Employees m
              on e.reports_to = m.employee_id
group by m.employee_id
order by employee_id

-- 10.查询指定日期的产品价格（第一次修改之前都是10）
-- Products-product_id、new_price、change_date
select p1.product_id, ifnull(p2.new_price, 10) as price
from (
         select distinct product_id
         from products
     ) as p1 -- 所有的产品
         left join (
    select product_id, new_price
    from products
    where (product_id, change_date) in (
        select product_id, max(change_date)
        from products
        where change_date <= '2019-08-16'
        group by product_id
    )
) as p2 -- 在 2019-08-16 之前有过修改的产品和最新的价格
        on p1.product_id = p2.product_id

-- 11.按顺序上巴士，巴士有1000kg的重量限制，找出最后一个可以上bus的人
-- Queue-person_id、person_name、weight、turn
SELECT a.person_name
FROM Queue a, Queue b
WHERE a.turn >= b.turn
GROUP BY a.person_id HAVING SUM(b.weight) <= 1000
ORDER BY a.turn DESC LIMIT 1

-- 12.以7天（从第七天开始）为一个时间段的顾客消费平均值（开窗函数）
-- Customers-customer_id、name、visited_on、amount
SELECT DISTINCT visited_on,
                sum_amount AS amount,
                ROUND(sum_amount/7, 2) AS average_amount
FROM (
         SELECT visited_on, SUM(amount) OVER ( ORDER BY visited_on RANGE interval 6 day preceding  ) AS sum_amount
         FROM Customer) t
WHERE DATEDIFF(visited_on, (SELECT MIN(visited_on) FROM Customer)) >= 6

-- 13.找出每个部门工资前三高的所有员工（可能有并列）
-- Employee-id、name、salary、departmentId，Department-id、name
select department.name as Department, employee.name as Employee, employee.salary as Salary
from employee,
     department,
     (select employee.id, dense_rank() over (PARTITION BY employee.departmentId order by salary desc) dr
      from employee) t
where employee.departmentId = department.id
  and t.dr <= 3
  and employee.id = t.id;

-- 14.查询每个日期销售的产品数量和产品详情
-- Activities-sell_date、product
select sell_date, count(distinct product) as num_sold, group_concat(distinct product order by product asc) as products
from Activities
group by sell_date
order by sell_date asc
