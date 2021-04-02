package socialnetwork;

import socialnetwork.domain.Board;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class User extends Thread {
    private final int sleepTime = 10;
    private static final AtomicInteger nextId = new AtomicInteger(0);

    protected final SocialNetwork socialNetwork;
    private final int id;
    private final String name;

    public User(String username, SocialNetwork socialNetwork) {
        this.name = username;
        this.id = User.nextId.getAndIncrement();
        this.socialNetwork = socialNetwork;
    }

    public int getUserId() {
        return id;
    }

    @Override
    public void run() {
        try {
            Random random = new Random(id);
            Set<User> users = socialNetwork.getAllUsers();
            User[] userArray = users.toArray(new User[users.size()]);
            Thread.sleep(random.nextInt(sleepTime));

            for (int i = 0; i < random.nextInt(5 - 1) + 1; i++) {
                Set<User> recipients = new HashSet<>();
                for (int j = 0; j < users.size(); j++) {
                    if (random.nextBoolean()) {
                        if (!userArray[j].equals(this)) {
                            recipients.add(userArray[j]);
                        }
                    }
                }
                socialNetwork.postMessage(this, recipients, "message numero: " + i + " from:" + this.name);
                Thread.sleep(random.nextInt(sleepTime));
            }

            Board board = socialNetwork.userBoard(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int msgCount() {
        Board board = socialNetwork.userBoard(this);
        return board.size();
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + '}';
    }

    @Override
    public int hashCode() {
        return id;
    }
}