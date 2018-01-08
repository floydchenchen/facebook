/*
*
* 57. Insert Interval
* https://leetcode.com/problems/insert-interval/description/
*
* 题目描述：
* Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
* You may assume that the intervals were initially sorted according to their start times.
*
* Example 1:
* Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].
*
* Example 2:
* Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].
* This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
*
* 思路
* 把所有endtime < newInterval.starttime的interval加到result中 （即newInterval之前的没有重叠的intervals）
*
* 把所有overlapping intervals和newInterval merge成一个新的newInterval，替代之前的newInterval，
* 加到result中（用较小的starttime和较大的endtime）
*
* 把其他starttime > newInterval.endtime加到result中去（即newInterval之后的没有重叠的intervals）
*
* */

package tag;

import java.util.ArrayList;
import java.util.List;

public class insert_intervals {
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> result = new ArrayList<>();
        int i = 0;
        // add all the intervals ending before newInterval starts
        while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
            result.add(intervals.get(i));
            i++;
        }
        // merge all overlapping intervals to the newInterval and make changes to it
        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
            // make changes to the newInterval
            newInterval = new Interval(
                    Math.min(newInterval.start, intervals.get(i).start),
                    Math.max(newInterval.end, intervals.get(i).end));
            i++;
        }
        // then add the newInterval to result
        result.add(newInterval);
        // add all other intervals (which starts after the end of the input "newInterval")
        while (i < intervals.size()) {
            result.add(intervals.get(i));
            i++;
        }
        return result;
    }

    // in-place solution: 即改变input List<Interval> intervals，上一个方法中的newInterval前后不受影响的不用动
    public List<Interval> insert2(List<Interval> intervals, Interval newInterval) {
        int i = 0;
        // 不要忘了 i < intervals.size()!!!!
        while (i < intervals.size() && intervals.get(i).end < newInterval.start) i++;
        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) { // <=: 刚好相等也需要merge
            // 创造一个新的interval:start是较小的start，end是较大的end
            newInterval = new Interval(Math.min(intervals.get(i).start, newInterval.start),
                                       Math.max(intervals.get(i).end, newInterval.end));
            // 不要忘了remove old interval!!! 同样以这样的方式来实现i++
            intervals.remove(i);
        }
        intervals.add(i, newInterval);
        return intervals;
    }
}
