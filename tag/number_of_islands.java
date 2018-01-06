/*
*
* 200. Number of Islands
* https://leetcode.com/problems/number-of-islands/
*
* 思路: flood fill algorithm (https://en.wikipedia.org/wiki/Flood_fill)
* method 1: DFS，又称Stack-based recursive implementation
* 如果grid[x][y]是1，以这个点为中心进行DFS，分别将四个方向的‘1’都标为0，然后最后DFS结束后counter++
*
* method 2: BFS
*
* 两种方法：time: O(mn), space: O(mn)
*
* */

package tag;

import java.util.LinkedList;
import java.util.Queue;

public class number_of_islands {

    // method 1: DFS search, everytime scanner meets 1, change 1 to 0
    public static int numIslands(char[][] grid) {
        int counter = 0;
        if (grid.length == 0) return counter;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] == '1') {
                    DFS(grid, x, y);
                    counter++;
                }
            }
        }
        return counter;
    }

    public static void DFS(char[][] grid, int x, int y) {
        // check boundary, if within boundary, change 1 to 0, then continue DFS
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == '0') return;
        // mark 1 off to 0
        grid[x][y] = '0';
        DFS(grid, x - 1, y);
        DFS(grid, x + 1, y);
        DFS(grid, x, y - 1);
        DFS(grid, x, y + 1);
    }

    // method 2: BFS
    public static int numIslands2(char[][] grid) {
        int counter = 0;
        if (grid.length == 0) return counter;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] == '1') {
                    BFS(grid, x, y);
                    counter++;
                }
            }
        }
        return counter;
    }

    public static void BFS(char[][] grid, int x, int y) {
        grid[x][y] = '0';
        int m = grid.length, n = grid[0].length;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(x * n + y);
        while (!queue.isEmpty()) {
            int position = queue.poll();
            int i = position / n, j = position % n;
            // check left boundary
            if (j > 0 && grid[i][j-1] == '1') {
                queue.add(i * n + j - 1);
                grid[i][j-1] = '0';
            }
            // check right boundary
            if (j < n-1 && grid[i][j+1] == '1') {
                queue.add(i * n + j + 1);
                grid[i][j+1] = '0';
            }
            // check upper boundary
            if (i > 0 && grid[i-1][j] == '1') {
                queue.add((i - 1) * n + j);
                grid[i-1][j] = '0';
            }
            // check lower boundary
            if (i < m-1 && grid[i+1][j] == '1') {
                queue.add((i + 1) * n + j);
                grid[i+1][j] = '0';
            }
        }
    }

    /*
    *
    * 变种1：find maximum island
    * 695. Max Area of Island (easy)
    *
    * */
    int max = 0, tempMax = 0;
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    tempMax = 0;
                    dfs(grid, i, j);
                }
            }
        }
        return max;
    }

    private void dfs(int[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == 0) return;
        grid[x][y] = 0;
        tempMax++;
        dfs(grid, x - 1, y);
        dfs(grid, x + 1, y);
        dfs(grid, x, y - 1);
        dfs(grid, x, y + 1);
        max = Math.max(max, tempMax);
    }


    /*
    *
    * 变种2：求一个岛的周长
    * 463. Island Perimeter (easy)
    *
    * */
    // method 1: dfs, time O(mn), space: O(mn)
    int result = 0;
    public int islandPerimeter1(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(grid, i, j, true);
            }
        }
        return result;
    }
    // boolean start: if grid[i][j] == 0, if start is true, this position is non-island, stop the dfs.
    // else if start is false, the dfs meets the boundary, result++, stop the dfs.
    private void dfs(int[][] grid, int i, int j, boolean start) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0) {
            if (start) return;
            else {
                result++;
                return;
            }
        }
        // mark visited position as -1, stop the dfs when the position is visited.
        if (grid[i][j] == -1) return;
        grid[i][j] = -1;
        dfs(grid, i-1, j, false);
        dfs(grid, i+1, j, false);
        dfs(grid, i, j-1, false);
        dfs(grid, i, j+1, false);
    }

    // method 2: count positions of island and positions that have neighbors
    // time: O(mn), space: O(1)
    public int islandPerimeter(int[][] grid) {
        int island = 0, neighbor = 0, m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    island++;
                    // only check right and lower neighbor
                    if (i < grid.length - 1 && grid[i+1][j] == 1) neighbor++;
                    if (j < grid[0].length - 1 && grid[i][j+1] == 1) neighbor++;
                }
            }
        }
        // 因为每有一个neighbor，就减少有2条重叠的边长
        return island * 4 - neighbor * 2;
    }

    /*
    *
    * follow-up：有很多islands，找出周长最大的island的周长
    *
    * */
    static int maxPerimiter = 0, tempPerimeter = 0;
    public static int LongestPerimeter(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                DFSLongestPerimeter(grid, i, j, true);
                maxPerimiter = Math.max(maxPerimiter, tempPerimeter);
                tempPerimeter = 0;
            }
        }
        return maxPerimiter;
    }

    private static void DFSLongestPerimeter(int[][] grid, int i, int j, boolean start) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0) {
            if (start) return;
            else {
                tempPerimeter++;
                return;
            }
        }
        // mark visited position as -1, stop the dfs when the position is visited.
        if (grid[i][j] == -1) return;
        grid[i][j] = -1;
        DFSLongestPerimeter(grid, i-1, j, false);
        DFSLongestPerimeter(grid, i+1, j, false);
        DFSLongestPerimeter(grid, i, j-1, false);
        DFSLongestPerimeter(grid, i, j+1, false);
    }


    public static void main(String[] args) {
        int[][] grid = {{1,1,0,0,0}, {1,1,0,0,0}, {0,0,1,0,0}, {0,0,0,1,1}};
        System.out.println(LongestPerimeter(grid));
    }
}
