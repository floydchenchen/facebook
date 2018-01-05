import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class number_of_islands_ii {
    // four directions for iteration，为了把2D array变成1D position的操作
    int[][] directions = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> result = new ArrayList<>();
        if (m <= 0 || n <= 0) return result;

        int islandCount = 0;
        // initialize each position in positions as one island whose parent is itself
        // parents[i]: i表示position，parents[i]的值代表这个position的parent
        int[] parents = new int[m * n];
        // -1 represent a non-island position
        Arrays.fill(parents, -1);

        // initialize the parent tree, whose parent is itself
        for (int[] position : positions) {
            int pos = position[0] * n + position[1];
            parents[pos] = pos;
            islandCount++;

            // union find process / build the parent tree
            for (int[] direction : directions) {
                int x = position[0] + direction[0];
                int y = position[1] + direction[1];
                int neighbor = x * n + y;
                // if the neighbor position does not exceed boundary and is a valid island position
                if (x >= 0 && x < m && y >= 0 && y < n && parents[neighbor] != -1) {
                    int neighborParent = find(neighbor, parents);
                    // if neighbor is currently another island
                    if (pos != neighborParent) {
                        // union two islands
                        // 注意要让新的neighborParent值变成pos的parent，而不是让pos变成neighborParent的parent
                        // 因为我们需要把neighborParent和pos所在的union联系起来
                        parents[pos] = neighborParent;
                        pos = neighborParent;
                        islandCount--;
                        System.out.println(islandCount);
                    }
                }
            }
            result.add(islandCount);
        }
        return result;
    }

    private int find(int pos, int[] parents) {
        return parents[pos] == pos ? pos : find(parents[pos], parents);
    }

    public static void main(String[] args) {
        int[][] pos = {{0,1},{1,2},{2,1},{1,0},{0,2},{0,0},{1,1}};
        number_of_islands_ii n = new number_of_islands_ii();
        System.out.println(n.numIslands2(3,3,pos).toString());
    }
}
