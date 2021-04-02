package socialnetwork.domain;

import socialnetwork.sortedlist.SortedList;

import java.util.List;
import java.util.concurrent.locks.StampedLock;

public class SimpleBoard implements Board {
    private SortedList<Message> messages;
    private StampedLock lock = new StampedLock();

    public SimpleBoard() {
        messages = new SortedList<>();
    }

    public boolean addMessage(Message message) {
        long stamp = lock.writeLock();
        try {
            return messages.add(message);
        } finally {
            lock.unlock(stamp);
        }
    }

    public boolean deleteMessage(Message message) {
        long stamp = lock.writeLock();
        try {
            return messages.remove(message);
        } finally {
            lock.unlock(stamp);
        }
    }

    public int size() {
        long stamp = lock.readLock();
        try {
            return messages.getLength();
        } finally {
            lock.unlock(stamp);
        }
    }

    public List<Message> getBoardSnapshot() {
        long stamp = lock.readLock();
        try {
            return messages.toList();
        } finally {
            lock.unlock(stamp);
        }
    }
}
