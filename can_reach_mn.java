/*
* 题目描述：给一个Pair (M, N) 代表坐标，你从(1, 1)出发，每次 (x, y) => (x + y, y) or (x, x + y)向右下方移动，
* 如果能达到(M, N)就是True，反之False.
*
* 分析：
* time: O(m+n)
* space: O(1)
*
* 思路：
* 从(M, N) 出发，M 和 N 必定一大一小，否则不可能满足上述条件。所以两者中较大是 X + Y， 较小是 X 或 Y。
* 由此从右下往左上反推，每一步都只可能有一个路径，所以最终能到达(1, 1)则为 True。
*
* */

public class can_reach_mn {
    public boolean canReachMN(int m, int n) {
        if (m == n || m < 1 || n < 1) return false;
        int[] pos = new int[]{m, n};
        while (pos[0] >= 1 && pos[1] >= 1) {
            setPreviousPos(pos);
            if (pos[0] == 1 && pos[1] == 1) return true;
        }
        return false;
    }

    private void setPreviousPos(int[] pos) {
        if (pos[0] > pos[1]) pos[0] -= pos[1];
        else pos[1] -= pos[0];
    }
}
