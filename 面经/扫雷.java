package 面经;/*
* Mine Sweeper 扫雷
* windows里面的扫雷，给一个h,w和m. 生成一个高度h，宽度w，总共m颗雷的矩阵。要求m颗雷随机分布。
*
* time: O(n)
* */

import java.util.Random;

public class 扫雷 {
    // reservoir sampling
    public int[][] putBomb(int h, int w, int count){
        Random r = new Random();
        int[] bombLocs = new int[count]; // bomb location array
        // 先放count个到里面
        for (int i = 0; i < count; i++)
            bombLocs[i] = i;
        // 再从count+1到最后，生成随机数，如果随机数小于count，就替换掉
        for (int i = count; i < h * w; i++) {
            int j = r.nextInt(i + 1);
            if (j < count)	bombLocs[j] = i;
        }
        int[][] res = new int[h][w];
        for (int i = 0; i < bombLocs.length; i++) {
            int x = bombLocs[i] / w;
            int y = bombLocs[i] % w;
            res[x][y] = 1;
        }
        return res;
    }
}
