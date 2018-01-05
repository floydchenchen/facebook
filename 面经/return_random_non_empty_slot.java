package 面经;/*
* 给你一个数字n, 代表从1->n  n个slot， 再给一个数组存着空slot的id. 让你同概率下randomly 返回一个非空的slot。
* example:
* n = 8, slots: 1,2,3,4,5,6,7,8.
* empty cells = {3, 5}
* non empty cells: 1,2,4,6,7,8
* return one of non empty cells randomly with equal probability.
*
* */

import java.util.Arrays;
import java.util.Random;

public class return_random_non_empty_slot {
    // time: O(eloge), where e is the size of emptyCell
    public static int returnEmptyCell(int slots , int[] emptyCell){
        Arrays.sort(emptyCell); // time: O(eloge)
        int rand = new Random().nextInt(slots - emptyCell.length) + 1;
        int counter = 0;
        for (int i : emptyCell) {
            if (rand + counter >= i) counter++;
            else break;
        }
        return rand + counter;
    }

    public static void main(String[] args) {
        int[] emptyCell  = {2,3,5};
        System.out.println(returnEmptyCell(8, emptyCell));
        System.out.println(returnEmptyCell(8, emptyCell));
        System.out.println(returnEmptyCell(8, emptyCell));
        System.out.println(returnEmptyCell(8, emptyCell));
        System.out.println(returnEmptyCell(8, emptyCell));
        System.out.println(returnEmptyCell(8, emptyCell));
    }
}
