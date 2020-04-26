package algorithm;

import java.util.PriorityQueue;

/**
 * @author pray chow
 * LeetCode 23 : 合并 K 个有序链表
 */
public class LeetCode23 {
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode root = new ListNode(-1), preNode = root;
        int k = lists.length;
        if (k == 0) {
            return root.next;
        }
        PriorityQueue<ListNode> queue = new PriorityQueue<>(k, (l1, l2) -> l1.val - l2.val);
        queue.offer(root);
        while (! queue.isEmpty()) {
            ListNode curNode = queue.poll();
            if (curNode.equals(root)) {
                for (int i = 0; i < k; ++i) {
                    if (lists[i] != null) {
                        queue.offer(lists[i]);
                    }
                }
            } else {
                preNode.next = curNode;
                if (curNode.next != null) {
                    queue.offer(curNode.next);
                }
                preNode = curNode;
            }
        }
        return root.next;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
