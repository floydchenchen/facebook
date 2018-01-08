/*
*
* 56. Merge Interval
* https://leetcode.com/problems/insert-interval/
*
* Given a collection of intervals, merge all overlapping intervals.
* For example,
* Given [1,3],[2,6],[8,10],[15,18],
* return [1,6],[8,10],[15,18].
*
* */

package tag;

import java.util.ArrayList;
import java.util.List;

public class merge_intervals {
    // find intersections
    // check if next interval's s and e are within the previous interval
    // time: O(nlgn), space: O(1)
    public List<Interval> merge(List<Interval> intervals) {
        // Sort by ascending starting point using an anonymous Comparator, lambda function
        intervals.sort((i1, i2) -> Integer.compare(i1.start, i2.start));
        // Arrays.sort(intervals, (a, b) -> a.start - b.start); // the same thing

        List<Interval> list = new ArrayList<>();
        if (intervals.size() == 0 || intervals == null) return list; // edge cases
        list.add(intervals.get(0));
        int i = 0; // pointer for old meetings in the list
        for (Interval interval : intervals) {
            // new meeting starts after old meeting ends
            if (interval.start > list.get(i).end) {
                list.add(interval);
                i++;
            } else { // new meeting starts before old meeting ends, merge end time
                if (interval.end > list.get(i).end) list.get(i).end = interval.end;
            }
        }
        return list;
    }

    /*
    *
    * 变种：返回总时间
    * input is unsorted and has some overlapping intervals, output is the [[[total non-overlapping time]]];
    *
    * O(nlogn) time, O(1) space
    *
    * */
    public int totalTime(List<Interval> intervals) {
        if (intervals == null || intervals.size() == 0) return 0;
        intervals.sort((a, b) -> a.start - b.start);
        int time = 0;
        Interval prev = new Interval(0, 0);
        for (Interval curr : intervals) {
            if (prev.end < curr.start) { // 新会议在老会议结束后开始
                time += curr.end - curr.start;
                prev = curr;
            } else if (curr.end > prev.end) { // 新会议在老会议结束前开始，并且比老会议晚结束，说明有overlap
                time += curr.end - prev.end;
                prev = curr;
            }
            // else: 其他情况，curr.end <= prev.end，说明新会议在老会议之中，什么都不做，也不updateprev
        }
        return time;
    }
}
