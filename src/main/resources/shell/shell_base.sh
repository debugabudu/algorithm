#!/bin/bash

#一、数值运算
#1.整数运算(求和，求模，求幂，进制转换):(())和let是shell内置命令，效率最高
i=0;
((i++))
let i++
echo $i

((i=5%2))
echo $i
echo 5 % 2 | bc

((i=5**2))
echo $i
echo "5^2" | bc

echo "obase=10;ibase=8;11" | bc -l
echo $((8#11))

#2.浮点运算
#scale=3保留3位小数，默认是整数，bc -l为浮点数，但20位
echo "scale=3; 1/13"  | bc
echo "1 13" | awk '{printf("%0.3f\n",$1/$2)}'

#3.随机数
echo $RANDOM
echo "" | awk '{srand(); printf("%f",rand());}'

#二、字符串操作
#计算字符串长度
var="get the length of me"
echo ${#var}
echo $var | awk '{printf("%d\n", length($0));}'
echo -n $var |  wc -c
#取子串
echo ${var:0:3}
echo ${var:(-2)}
echo $var | awk '{printf("%s\n", substr($0, 9, 6))}'
echo $var | cut -d" " -f 5
#子串替换
echo ${var/ /_}        #把第一个空格替换成下划线
echo ${var// /_}       #把所有空格都替换成下划线
echo $var | sed -e 's/ /_/'
echo $var | sed -e 's/ /_/g'
#子串排序
echo $var | tr ' ' '\n' | sort   #正序排
echo $var | tr ' ' '\n' | sort -r #反序排

