-- 学生表: Students
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | student_id    | int     |
-- | student_name  | varchar |
-- +---------------+---------+
-- 在 SQL 中，主键为 student_id（学生ID）。
-- 该表内的每一行都记录有学校一名学生的信息。
--
-- 科目表: Subjects
-- +--------------+---------+
-- | Column Name  | Type    |
-- +--------------+---------+
-- | subject_name | varchar |
-- +--------------+---------+
-- 在 SQL 中，主键为 subject_name（科目名称）。
-- 每一行记录学校的一门科目名称。
--
-- 考试表: Examinations
-- +--------------+---------+
-- | Column Name  | Type    |
-- +--------------+---------+
-- | student_id   | int     |
-- | subject_name | varchar |
-- +--------------+---------+
-- 这个表可能包含重复数据（换句话说，在 SQL 中，这个表没有主键）。
-- 学生表里的一个学生修读科目表里的每一门科目。
-- 这张考试表的每一行记录就表示学生表里的某个学生参加了一次科目表里某门科目的测试。
--
-- 查询出每个学生参加每一门科目测试的次数，结果按 student_id 和 subject_name 排序。
-- 结果需要包含所有学生和所有科目，即使某个学生没有参加某门科目的测试。
select s.student_id, s.student_name, s.subject_name, count(e.subject_name) as attended_exams
from (
         select student_id, student_name, subject_name
         from Students
                  cross join Subjects
     ) s
         left join Examinations e
                   on s.student_id = e.student_id and s.subject_name = e.subject_name
group by student_id, subject_name
order by student_id, subject_name