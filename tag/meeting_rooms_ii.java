/*
*
* 253. Meeting Rooms II
* https://leetcode.com/problems/meeting-rooms-ii/description/
*
* Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
* find the minimum number of conference rooms required.
* For example,
* Given [[0, 30],[5, 10],[15, 20]],
* return 2.
*
* */

package tag;

import java.util.*;

public class meeting_rooms_ii {
    /*
        简单版，meeting rooms I, return true or false
    */
    public boolean canAttendMeetings(Interval[] intervals) {
        // Sort the intervals by start time
        // 把start time 排序
        // start time at index i should be no less than end time at index (i+1)
        // time: O(nlgn) due to sorting, space: O(1)
        Arrays.sort(intervals, (a, b) -> a.start - b.start);

        // start time at index i should be no less than end time at index (i+1)
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start < intervals[i - 1].end) return false;
        }
        return true;
    }

    /*
    * meeting rooms II, return how many rooms needed
    *
    * method 1: sorting
    *   start time, end time 排序
    *   如果有一个start time < 某个end time，就需要一个房间
    *   如果有两个start time < 某个end time，就需要两个房间
    *   loop over starttime list，需要两个var: rooms, endTimePointer
    *       每当startime[i] < endTime[endTimePointer], rooms++ 【starttime[i]是新的meeting, endTime[endTimePointer]是旧的meeting】
    *       else endTimePointer++
    *
    * method 2: min heap
    *   loop over intervals (with sorted start time)
    *       如果intervals[i].starttime >= interval_polled.endtime【intervals[i].starttime是新的meeting, heap.poll()是旧的meeting
    *       这时需要merge end time: interval_polled.end = intervals[i].end
    *       else heap.offer(intervals[i]);
    *       heap.offer(interval_polled)
    *
    * */

    // method 1: sorting
    // time: O(nlgn), space: O(n)
    public int minMeetingRooms1(Interval[] intervals) {
        int[] startTime = new int[intervals.length];
        int[] endTime = new int[intervals.length];
        // put starttime and endtime in each array
        for (int i = 0; i < intervals.length; i++) {
            startTime[i] = intervals[i].start;
            endTime[i] = intervals[i].end;
        }
        Arrays.sort(startTime);
        Arrays.sort(endTime);

        int rooms = 0;
        int endTimeIndexPointer = 0;
        // two pointers: i is starttime pointer, endTimeIndexPointer is endtime pointer
        for (int i = 0; i < startTime.length; i++) {
            if (startTime[i] < endTime[endTimeIndexPointer]) { // start before end, so need one more room
                rooms++;
            } else { // end before start, do not need more rooms, move to next endTime
                endTimeIndexPointer++;
            }
        }
        return rooms;
    }

    // method 2: min heap
    // time: O(nlgn), space: O(n)
    public int minMeetingRooms2(Interval[] intervals) {
        if (intervals == null || intervals.length == 0)
            return 0;

        // Sort the intervals by start time
        Arrays.sort(intervals, (a, b) -> a.start - b.start);

        // Use a min heap to track the minimum [end time] of merged intervals
        // 同时minHeap的size也是需要用几间房！
        PriorityQueue<Interval> heap = new PriorityQueue<Interval>((a, b) -> a.end - b.end);

        // start with the first meeting, put it to a meeting room
        heap.offer(intervals[0]);

        // i tracks current meeting
        for (int i = 1; i < intervals.length; i++) {
            // get the meeting room that finishes earliest
            Interval interval = heap.poll();

            if (intervals[i].start >= interval.end) {
                // if the current meeting starts right after
                // there's no need for a new room, merge the interval
                interval.end = intervals[i].end;
            } else { // current meeting starts before old meeting ends
                // otherwise, this meeting needs a new room
                heap.offer(intervals[i]);
            }

            // don't forget to put the meeting room back after looking
            heap.offer(interval);
        }

        return heap.size();
    }

    /*
    *
    * 变种1：
    * Return the exact [time] that has max number of room used (any valid time)
    * 和原题基本一样，只需想明白【【【最后一次overlapStart - overlapEnd之间的任意时间都使用了最大数目的房间。】】】
    * 所以you can return any time between overlapStart and overlapEnd
    *
    * */
    // time: O(nlgn), space: O(n)
    public static int minMeetingRoomsTime(Interval[] intervals) {
        if (intervals == null || intervals.length == 0)
            return 0;

        // Sort the intervals by start time
        Arrays.sort(intervals, (a, b) -> a.start - b.start);

        // Use a min heap to track the minimum [end time] of merged intervals
        PriorityQueue<Interval> heap = new PriorityQueue<Interval>((a, b) -> a.end - b.end);

        // start with the first meeting, put it to a meeting room
        heap.offer(intervals[0]);

        // 新加的变量
        int overlapStart = -1, overlapEnd = -1;

        // i tracks current meeting
        for (int i = 1; i < intervals.length; i++) {
            // get the meeting room that finishes earliest
            Interval interval = heap.poll();

            if (intervals[i].start >= interval.end) {
                // if the current meeting starts right after
                // there's no need for a new room, merge the interval
                interval.end = intervals[i].end;
            } else { // current meeting starts before old meeting ends
                // otherwise, this meeting needs a new room
                overlapStart = intervals[i].start;
                overlapEnd = Math.min(interval.end, intervals[i].end);
                heap.offer(intervals[i]);
            }

            // don't forget to put the meeting room back after looking
            heap.offer(interval);
        }

        return overlapStart; //you can return any time between overlapStart and overlapEnd
    }

    /*
    *
    * 变种2：
    * Return the numbers of room used / employees of each non-overlapping time range(you can design you own output)
    * eg. [2004, 2007], [2005, 2007], [2006, 2008] -> 2004-2005: 1; 2005-2006: 2; 2006-2007: 3; 2007-2008: 1;
    *
    * 如果the format of intervals are "March, 2014" etc, first convert it to "201403" by "2014" + "03"(hashmap:March->03)
    * http://www.1point3acres.com/bbs/thread-109379-2-1.html
    * 问(1,2),(2,4),(3,5)该输出["1-2:1","2-3:1","3-4:2","4-5:1"]还是["1-3:1","3-4:2","4-5:1"]
    * 下面的代码是输出["1-2:1","2-3:1","3-4:2","4-5:1"]的。
    *
    *
    * 思路：新建一个数据结构TimeSlot
    *
    * */

    // int time, boolean isStart
    public class TimeSlot {
        int time;
        boolean isStart;
        public TimeSlot(int t, boolean i) {
            time = t;
            isStart = i;
        }
    }

    public List<String> meetingRoomsList(Interval[] intervals) {
        List<String> result = new ArrayList<>();
        if (intervals == null || intervals.length == 0)    return result;
        List<TimeSlot> times = new ArrayList<>();
        for (Interval i : intervals) {
            // 时间是start, isStart为true; end的话为false
            times.add(new TimeSlot(i.start, true));
            times.add(new TimeSlot(i.end, false));
        }
        // sort each time
        Collections.sort(times, (a, b) -> a.time - b.time);

        int count = 1, startIdx = 0;//it's the index of begin time, not the time itself
        for (int i = 1; i < times.size(); i++) {
            if (times.get(i).time != times.get(i - 1).time) {
                result.add(times.get(startIdx).time + "-" + times.get(i).time + ": " + count);
                startIdx = i;
            }
            if (times.get(i).isStart) count++;
            else count--;
        }
        return result;
    }


    /*
    *
    * 变种3：
    * print each room usage time intervals: Room 1:[2, 6],[7, 9]; Room 2:[3, 5],[8, 10]; etc.
    * 返回每个房间 所有的会议 的开始时间和结束时间。
    * */



    public static void main(String[] args) {
        Interval i1 = new Interval(0, 30);
        Interval i2 = new Interval(5, 20);
        Interval i3 = new Interval(15, 20);
        Interval[] intervals = new Interval[] {i1, i2, i3};
        System.out.println(minMeetingRoomsTime(intervals));
    }
}
