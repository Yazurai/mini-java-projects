package socialnetwork;

import socialnetwork.domain.Backlog;
import socialnetwork.domain.Task;
import socialnetwork.sortedlist.SortedList;

import java.util.Optional;
import java.util.concurrent.locks.StampedLock;

public class SimpleBacklog implements Backlog {
    private SortedList<Task> tasks;
    private StampedLock lock = new StampedLock();

    public SimpleBacklog() {
        tasks = new SortedList<>();
    }

    synchronized public boolean add(Task task) {
        long stamp = lock.writeLock();
        try {
            return tasks.add(task);
        } finally {
            lock.unlock(stamp);
        }
    }

    synchronized public Optional<Task> getNextTaskToProcess() {
        long stamp = lock.readLock();
        try {
            if (tasks.getLength() != 0) {
                Task task = tasks.getValue(0);
                stamp = lock.tryConvertToWriteLock(stamp);
                if (stamp == 0L) {
                    stamp = lock.writeLock();
                }
                tasks.remove(0);
                return Optional.of(task);
            } else {
                return Optional.empty();
            }
        } finally {
            lock.unlock(stamp);
        }
    }

    synchronized public int numberOfTasksInTheBacklog() {
        long stamp = lock.readLock();
        try {
            return tasks.getLength();
        } finally {
            lock.unlock(stamp);
        }
    }
}
