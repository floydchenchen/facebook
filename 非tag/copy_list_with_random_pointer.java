/*
*
* A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
* Return a deep copy of the list.
*
* */

package 非tag;

import java.util.HashMap;

public class copy_list_with_random_pointer {
    // method 1: hashmap + 2 iteration
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) return null;
        HashMap<RandomListNode, RandomListNode> map = new HashMap<>();

        // loop 1. copy all the nodes
        RandomListNode node = head;
        while (node != null) {
            map.put(node, new RandomListNode(node.label));
            node = node.next;
        }

        // loop 2. assign next and random pointers
        node = head;
        while (node != null) {
            map.get(node).next = map.get(node.next);
            map.get(node).random = map.get(node.random);
            node = node.next;
        }
        return map.get(head);
    }
}
