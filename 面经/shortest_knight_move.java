package 面经;/*
* 题目描述：一个infinite的棋盘上给两个点，A和B，问一个Knight（骑马的那个，走L形），最少要几步从A跳到B？
* 如果目前位置是(x,y)那么可以到以下任意一个位置：
* (x-2,y-1); (x-2,y+1);
* (x+2,y-1);(x+2,y+1);
* (x-1,y+2);(x-1,y-2);
* (x+1,y-2);(x+1,y+2).
*
* 分析：
* time: O(8^n), n是start到end的move次数
* space: O(8^n)
*
* 思路：
* 因为是infinite matrix，所以DP、DFS不可行，用BFS做
* 1. a list of directions (int[])
* 2. a queue for BFS
* 3. a set to keep track of visited positions
* 4. an int move to keep track of current level of BFS
* 每次while loop iteration，对现在的层数的所有node进行BFS
* */

import java.util.*;


public class shortest_knight_move {
    public static int shortestKnightMove(int x1, int y1, int x2, int y2) {
        int[][] directions = {{1,2},{1,-2},{-1,2},{-1,-2},{2,1},{2,-1},{-2,1},{-2,-1}};
        Set<int[]> visited = new HashSet<>();
        int[] start = new int[]{x1, y1};
        visited.add(start);
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(start);
        int move = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                for (int[] direction : directions) {
                    int x = pos[0] + direction[0], y = pos[1] + direction[1];
                    if (x == x2 && y == y2) return move + 1;
                    int[] newPos = new int[]{x, y};
                    if (visited.contains(newPos)) continue;
                    visited.add(newPos);
                    queue.offer(newPos);
                }
            }
            move++;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(shortestKnightMove(0,0, 3,5));
    }
}
