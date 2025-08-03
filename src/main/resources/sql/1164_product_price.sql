-- 产品数据表: Products
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | product_id    | int     |
-- | new_price     | int     |
-- | change_date   | date    |
-- +---------------+---------+
-- (product_id, change_date) 是此表的主键（具有唯一值的列组合）。
-- 这张表的每一行分别记录了 某产品 在某个日期 更改后 的新价格。
-- 一开始，所有产品价格都为 10。
--
-- 编写一个解决方案，找出在 2019-08-16 所有产品的价格。
-- 以 任意顺序 返回结果表。
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