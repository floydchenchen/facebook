/*
*
* 261. Graph Valid Tree
* https://leetcode.com/problems/graph-valid-tree/
*
* Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
* write a function to check whether these edges make up a valid tree.
*
* For example:
* Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
* Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
*
* assume no duplicate edges, e.g. [0,1] and [1,0] are duplicate
* the conditions for a valid tree:
*    1. all nodes are connected to root
*    2. no forward or backward nodes（也就是么有cycle）
*    n >= 2
*
* 点比边个数多一个!!!!!!!
* 从一个 node 到另一个 node，必须有且只有一个 unique path
* 用一个adjacent list (a representation of graph)
* */

package tag;

import java.util.*;

public class graph_valid_tree {

    // method 1: union find
    // time: O(VE) V is the number of vertices, E is the number of edges
    public static boolean validTree1(int n, int[][] edges) {
        // initialize n isolated islands with -1
        int[] nodes = new int[n];
        Arrays.fill(nodes, -1);

        // perform union find
        for (int i = 0; i < edges.length; i++) {
            // 相当于从edge连接的两个node分别去找两个node的parent
            int x = find(nodes, edges[i][0]);
            int y = find(nodes, edges[i][1]);
            System.out.println("edge: " + i + ", x: " + x + ", y: " + y);

            // if two vertices happen to be in the same set
            // then there's a cycle
            if (x == y) return false;

            // union
            nodes[y] = x;
            System.out.println(Arrays.toString(nodes));
        }

        return edges.length == n - 1;
    }
    // union find helper method
    // time: O(v), v is the number of the vertices
    static private int find(int nodes[], int i) {
        return nodes[i] == -1 ? i : find(nodes, nodes[i]);
    }

    // method 2: BFS + build graph
    // time: O(VE) space: O(VE)
    public static boolean validTree2(int n, int[][] edges) {
        // 点必须比边数多一个！
        // 从一个node到另一个node，必须有且只有一个unique path
        // 这个方法的思路是，从任意一点出发，必须能到其他所有的点
        // 能到达的点，加到set里面，最后看能到达的点的数量是否是n
        if (n == 0 || edges.length != n - 1) return false;
        // 用map来建graph
        Map<Integer, Set<Integer>> graph = initializeGraph(n, edges);
        // 里面存的node的数字key
        Queue<Integer> queue = new LinkedList<>();
        //
        Set<Integer> visitedNodes = new HashSet<>();
        queue.add(0);
        visitedNodes.add(0);
        while(!queue.isEmpty()) {
            int key = queue.poll();
            for (Integer node : graph.get(key)) {
                if (visitedNodes.contains(node)) continue;
                visitedNodes.add(node);
                queue.add(node);
            }
        }
        return (visitedNodes.size() == n);
    }

    public static Map<Integer, Set<Integer>> initializeGraph(int n, int[][] edges) {
        Map<Integer, Set<Integer>> graph = new HashMap<Integer, Set<Integer>>();
        for (int i = 0; i < n; i++) graph.put(i, new HashSet<Integer>());

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        return graph;
    }

    // method 3: DFS, 没细看，网上找的
    // time: O(VE) space: O(VE)
    public static boolean validTree3(int n, int[][] edges) {
        List<List<Integer>> adjList = new ArrayList<>();
        //if (edges.length == 0 && n == 1)  return true;
        if (edges.length != n - 1)  return false;
        for (int i = 0; i < n; i++)
            adjList.add(i, new LinkedList<>());
        for (int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
            adjList.get(edge[1]).add(edge[0]);
        }
        boolean[] visited = new boolean[n];
        if (hasCycle(adjList, visited, 0, -1))  return false; // here use 0 as graph source
        for (int i = 0; i < n; i++) // but 0 may not be in 'adjList', so should check if all vertices are visited,  e.x.[[1,2],[2,3],[1,3]]
            if (!visited[i])    return false;
        return true;
    }
    public static boolean hasCycle(List<List<Integer>> adjList, boolean[] visited, int i, int pre) {
        visited[i] = true;
        for (int v : adjList.get(i))
            if (v != pre && visited[v] || !visited[v] && hasCycle(adjList, visited, v, i)) return true;
        return false;
    }


    public static void main(String[] args) {
        int[][] edges = {{0, 1}, {0, 2}, {0, 3},{1,4}};
        System.out.println(validTree3(5, edges));
    }
}
