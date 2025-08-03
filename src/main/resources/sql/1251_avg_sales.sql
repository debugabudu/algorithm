-- 表：Prices
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | product_id    | int     |
-- | start_date    | date    |
-- | end_date      | date    |
-- | price         | int     |
-- +---------------+---------+
-- (product_id，start_date，end_date) 是 prices 表的主键（具有唯一值的列的组合）。
-- prices 表的每一行表示的是某个产品在一段时期内的价格。
-- 每个产品的对应时间段是不会重叠的，这也意味着同一个产品的价格时段不会出现交叉。
--
-- 表：UnitsSold
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | product_id    | int     |
-- | purchase_date | date    |
-- | units         | int     |
-- +---------------+---------+
-- 该表可能包含重复数据。
-- 该表的每一行表示的是每种产品的出售日期，单位和产品 id。
--
-- 编写解决方案以查找每种产品的平均售价。average_price 应该 四舍五入到小数点后两位。如果产品没有任何售出，则假设其平均售价为 0。
-- 返回结果表 无顺序要求 。
SELECT
    product_id,
    IFNULL(Round(SUM(sales) / SUM(units), 2), 0) AS average_price
FROM (
         SELECT
             p.product_id AS product_id,
             p.price * u.units AS sales,
             u.units AS units
         FROM Prices p
                  LEFT JOIN UnitsSold u ON p.product_id = u.product_id
             AND (u.purchase_date BETWEEN p.start_date AND p.end_date)
     ) T
GROUP BY product_id