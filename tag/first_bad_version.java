/*
*
* 278. First Bad Version
* https://leetcode.com/problems/first-bad-version/
*
* */

package tag;

public class first_bad_version {
    // API， 随便return一个值
    boolean isBadVersion(int version) {
        return true;
    }

    // 简单版, binary search
    // time: O(lgn), space: O(1)
    public int firstBadVersion1(int n) {
        if (n < 1) return -1;
        int start = 0, end = n;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // start ... fb ... mid ... end
            if (isBadVersion(mid)) end = mid;
                // start ... mid ... fb ... end
            else start = mid;
        }
        if (isBadVersion(start)) return start;
        if (isBadVersion(end)) return end;
        return -1;
    }

    /*
    *
    * 变种1：如果不知道一共有多少版本的情况下应该怎么找。 Unknown number of version
    *
    * 思路：利用binary search的思路，先进行一个"反向binary search"增加区间
    * 即定义start和end pointer都为0，
    * 让end pointer从0开始，每次让end的值乘以2，如果没找到区间，start = end；如果找到了，在这个区间做binary search
    *
    * */
    // time: O(nlgn)，因为增大和收缩的操作都是O(nlgn)，所以结果还是O(nlgn)
    public int firstBadVersion2() {
        int start = 0, end = 1;
        // 更新start和end的区间，直到找到fb的区间
        while (!isBadVersion(end)) { // time: O(nlgn)
            start = end;
            end *= 2;
        }
        // binary search
        while (start + 1 < end) { // time: O(nlgn)
            int mid = start + (end - start) / 2;
            if (isBadVersion(mid)) end = mid;
            else start = mid;
        }
        if (isBadVersion(start)) return start;
        if (isBadVersion(end)) return end;
        return -1;
    }
}
