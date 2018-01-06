/*
*
* Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.
*
* */

package tag;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class clone_graph {
    // BFS method,
    // time: O(ne), n is the number of nodes, e is the average size of a node's edges, space: O(ne)
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) return null;
        // map: int -> UndirectedGraphNode
        HashMap<Integer, UndirectedGraphNode> map = new HashMap<>();
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        // make a new node, put in map and queue
        UndirectedGraphNode root = new UndirectedGraphNode(node.label);
        map.put(root.label, root);
        queue.add(node);

        while (!queue.isEmpty()) {
            UndirectedGraphNode current = queue.poll();
            for (UndirectedGraphNode neighbor : current.neighbors) {
                // 如果Map不包含neighbor的key, create one, put in queue
                if (!map.containsKey(neighbor.label)) {
                    map.put(neighbor.label, new UndirectedGraphNode(neighbor.label));
                    queue.add(neighbor);
                }
                // add neighbor to current's list of neighbors
                map.get(current.label).neighbors.add(map.get(neighbor.label));
            }
        }
        return root;
    }

    // DFS
    public UndirectedGraphNode cloneGraph2(UndirectedGraphNode node) {
        Map<Integer, UndirectedGraphNode> map = new HashMap<>();
        return DFSClone(node, map);
    }

    public UndirectedGraphNode DFSClone(UndirectedGraphNode node, Map<Integer, UndirectedGraphNode> map) {
        // base cases: node is null / map contains node's label
        if (node == null)	return null;
        if (map.containsKey(node.label))	return map.get(node.label);

        UndirectedGraphNode cloneNode = new UndirectedGraphNode(node.label);
        map.put(cloneNode.label, cloneNode);
        // dfs on every neighbor
        for (UndirectedGraphNode n : node.neighbors)
            cloneNode.neighbors.add(DFSClone(n, map));
        return cloneNode;
    }
}
