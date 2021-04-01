package com.yliu.algorithm.usually;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 贪心
 */
public class Greedy {
    /**
     * 例题-训练部队*
     * 为了训练出精锐的部队,将军会对新兵进行训练。部队进入了n个新兵,每个新兵有一个战斗力值和潜力值,当两个新兵进行决斗时,总是战斗力值高的获胜。
     * 获胜的新兵的战斗力值就会变成对手的潜力值 + 自己的战斗力值 - 对手的战斗力值。败者将会被淘汰。
     * 若两者战斗力值一样,则会同归于尽,双双被淘汰(除了考察的那个新兵之外，其他新兵之间不会发生战斗) 。
     * 通过互相决斗之后新兵中战斗力值+潜力值最高的一个可能达到多少。
     */
    public void train() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Entity[] a = new Entity[n];
        for (int i = 0; i < n; i++) {
            a[i] = new Entity(sc.nextInt(), sc.nextInt());
        }
        //按战力值从小到大排序，战力值相同的按潜力值排序
        Arrays.sort(a, (o1, o2) -> o1.x-o2.x!=0 ? o1.x-o2.x : o1.y-o2.y);
        int max = 0; //x+y的最大值
        int index = 0; // 下标
        int cha = 0; // 差值
        //找到最牛逼的那个人
        for (int i = 0; i < n; i++) {
            if (a[i].x + a[i].y >= max || a[i].x + a[i].y + cha >= max) {
                max = a[i].x + a[i].y;
                cha = a[i].y - a[i].x;
                index = i;
            }
        }
        long res = max;
        //把他之前的所有可以产生正收益的人干掉
        for (int i = index-1; i >= 0 ; i--) {
            if (a[index].x!=a[i].x&&a[i].y > a[i].x) {
                res += a[i].y - a[i].x;
            }
        }

        System.out.println(res);
    }
    static class Entity {
        int x;
        int y;
        Entity(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
