import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class RandomizedSet {
    // key is val, value is the val's location
    HashMap<Integer, Integer> map;
    // 因为要让getRandom() O(1)，所以需要一个list来存locations[i]的val是啥，然后使用list的size
    ArrayList<Integer> locations;
    Random random = new Random();
    /** Initialize your data structure here. */
    public RandomizedSet() {

    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        boolean contain = map.containsKey(val); // ????为啥必须要这个？
        if (contain) return false;
        map.put(val, locations.size());
        locations.add(val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        boolean contain = map.containsKey(val);
        if (!contain) return false;
        int location = map.get(val);
        // if the one removed is not the last location, sway them and remove it
        if (location < locations.size() - 1) {
            int lastVal = locations.get(locations.size() - 1);
            locations.set(location, lastVal);
            map.put(lastVal, location);
        }
        map.remove(val);
        locations.remove(locations.size() - 1);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return locations.get(random.nextInt(locations.size()));
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */