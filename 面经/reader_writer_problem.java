package 面经;/*
* implement: read_lock()/read_unlock() and write_lock()/write_unlock()
* 基本上来说，不能同时读和取
* given: Mutex.lock and Mutex.unlock
*
*
* If one of the people tries editing the file, no other person should be reading or writing at the same time,
* otherwise changes will not be visible to him/her.
* However if some person is reading the file, then others may read it at the same time.
*
* readerCount: the number of processes performing read
* */

public class reader_writer_problem {
    interface Mutex {
        void lock();
        void unlock();
    }

    private int readerCounter = 0;
    private Mutex counterMutex;
    private Mutex readerWriteMutex;

    public void read_lock() {
        counterMutex.lock();
        if (readerCounter == 0) {
            readerCounter++;
            readerWriteMutex.lock();
        }
        else readerCounter++;
        counterMutex.unlock();
    }

    public void read_unlock() {
        counterMutex.lock();
        readerCounter--;
        if (readerCounter == 0) readerWriteMutex.unlock();
        counterMutex.unlock();
    }

    public void write_lock() {
        readerWriteMutex.lock();
    }

    public void write_unlock() {
        readerWriteMutex.unlock();
    }
}
