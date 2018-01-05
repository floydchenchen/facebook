/*
* 一个m x n 的 array 只有 0 和 1  给一个 int k
* 需要把 小于 k 数量 连续的 1 变成 0
* 连续： 上下左右和四个斜线方向
*
* 这题说的是，当island的面积小于k的时候，把1翻成0
*
* */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class remove_island_below_area_k {

    // BFS, time: O(mn), space: O(mn)
    public static void removeIsland(int[][] points, int k) {
        int m = points.length; int n= points[0].length;
        boolean[][] visited = new boolean[m][n];
        int[] dx = {-1,-1,-1,0,0,1,1,1};
        int[] dy = {-1,0,1,-1,1,-1,0,1};
        for(int i = 0;i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (points[i][j] == 1 && !visited[j][j]) {
                    Queue<int[]> queue = new LinkedList<>();
                    queue.add(new int[]{i, j});
                    ArrayList<int[]> record = new ArrayList<>();
                    record.add(new int[]{i, j});
                    while (!queue.isEmpty()) {
                        int[] point = queue.poll();
                        int x = point[0];
                        int y = point[1];
                        visited[x][y] = true;

                        for (int dirIndex = 0; dirIndex < 8; dirIndex++) {
                            int dir_x = x + dx[dirIndex];
                            int dir_y = y + dy[dirIndex];
                            if (dir_x >= 0 && dir_x < m && dir_y >= 0 && dir_y < n && points[dir_x][dir_y] == 1 && !visited[dir_x][dir_y]) {
                                queue.add(new int[]{dir_x, dir_y});
                                record.add(new int[]{dir_x, dir_y});
                            }

                        }
                    }
                    if (record.size() < k) {
                        for (int[] p : record) {
                            points[p[0]][p[1]] = 0;
                        }
                    }
                    record.clear();
                }
            }
        }
    }

    public static void main(String[] args){
        int[][] matrix = {{1,0, 0, 0}, {1, 0, 0, 0}, {1, 0, 1, 1}};
        for(int[] a : matrix)
            System.out.println(Arrays.toString(a));
        System.out.println("=========");
        removeIsland(matrix, 3);
        for(int[] a : matrix)
            System.out.println(Arrays.toString(a));
    }

}
