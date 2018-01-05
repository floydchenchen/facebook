package 面经;/*
* 题目描述：check一个图形是不是bipartite的图
* 例如：下图就是bipartite
* 1---2—--1
* |       |
* |       |
* 2---1---2
*
* 分析：
* time: O(mn)
* space: O(mn)
* 思路：
* BFS
*
* */


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class check_bipartite_graph {

    final static int V = 4; // we define the # of Vertices
    // This function returns true if graph G[V][V] is Bipartite, else false
    public static boolean isBipartite(int G[][],int src) {
        // Create a color array to store colors assigned to all veritces.
        // Vertex number is used as index in this array. The value '-1'
        // of  colorArr[i] is used to indicate that no color is assigned
        // to vertex 'i'.  The value 1 is used to indicate first color
        // is assigned and value 0 indicates second color is assigned.
        int colorArr[] = new int[V];
        Arrays.fill(colorArr, -1);

        // Assign first color to source
        colorArr[src] = 1;

        // Create a queue (FIFO) of vertex numbers and enqueue
        // source vertex for BFS traversal
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(src);

        // Run while there are vertices in queue (Similar to BFS)
        while (!q.isEmpty()) {
            int u = q.poll();

            // Return false if there is a self-loop
            if (G[u][u] == 1) return false;

            // Find all non-colored adjacent vertices
            for (int v = 0; v < V; v++) {
                // An edge from u to v exists and destination v is not colored
                if (G[u][v] == 1 && colorArr[v] == -1) {
                    // Assign alternate color to this adjacent v of u
                    colorArr[v] = 1 - colorArr[u];
                    q.add(v);
                }

                // An edge from u to v exists and destination v is
                // colored with same color as u
                else if (G[u][v] == 1 && colorArr[v] == colorArr[u]) return false;
            }
        }
        return true;
    }

    public static void main (String[] args) {
        int G[][] = {{0, 1, 0, 1},
                     {1, 0, 1, 0},
                     {0, 1, 0, 1},
                     {1, 0, 1, 0}};
        if (isBipartite(G, 0))
            System.out.println("Yes");
        else
            System.out.println("No");
    }
}
