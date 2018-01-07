/*
*
* 277. Find the Celebrity
* https://leetcode.com/problems/find-the-celebrity/description/
*
* 题目描述：所有其他人都认识名人，名人不认识任何其他人，从n个人(0 ~ (n-1))里面找出名人，如果没有名人，return -1
*
* find universal sink in directed graph
*
* */

package tag;

public class find_the_celebrity {
    // two pass, time O(n)
    // 第一个for loop找temp celebrity
    // 第二个for loop判断他是不是真正的celebrity
    public int findCelebrity(int n) {
        // 先随便定义一个temp celebrity
        // 用箭头画一个图的话，这个celebrity相当于directed graph里面的一个universal sink，所有的
        int tempCelebrity = 0;
        // find (temp) celebrity
        for (int i = 0; i < n; i++) {
            if (knows(tempCelebrity, i)) tempCelebrity = i;
        }
        // check if if tempCelebrity is a real celebrity
        // 检查这个temp sink是否是真的universal sink
        for (int i = 0; i < n; i++) {
            // 如果（i不是名人 && 名人认识i）|| (i不认识名人)
            // 如果 (i不是sink && sink指向i) || (i不指向sink)
            if (i != tempCelebrity && (knows(tempCelebrity, i) || !knows(i,tempCelebrity))) return -1;
        }
        return tempCelebrity;
    }

    // 使用这个API，为了不报错随便return了一个值，return的是a是否认识b
    private boolean knows (int a, int b) {
        return true;
    }
}
