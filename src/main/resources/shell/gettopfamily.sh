#!/bin/bash

###
 #1 3 4490
 #2 5 3896
 #3 4 3112
 #4 4 4716
 #5 4 4578
 #6 6 5399
 #7 3 5089
 #8 6 3029
 #9 4 6195
 #10 5 5145
 # 家庭编号 家庭人数 家庭月收入，存在一个txt文件中，找到人均月收入最高的
###

# shellcheck disable=SC2242
[ $# -lt 1 ] && echo "please input the income file" && exit -1
[ ! -f $1 ] && echo "$1 is not a file" && exit -1

income=$1
awk '{
    printf("%d %0.2f\n", $1, $3/$2);
}' $income | sort -k 2 -n -r