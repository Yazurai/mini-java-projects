package socialnetwork;

import socialnetwork.domain.*;

public class Main {
    private static final int workerCount = 5;
    private static final int userCount = 5;

    public static void main(String[] args) {
        // Implement logic here following the steps described in the specs
        SimpleBacklog backlog = new SimpleBacklog();
        SocialNetwork socialNetwork = new SocialNetwork(backlog);

        Worker[] workerThreads = new Worker[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workerThreads[i] = new Worker(backlog);
            workerThreads[i].start();
        }

        User[] userThreads = new User[userCount];
        for (int i = 0; i < userCount; i++) {
            User newUser = new User("User" + i, socialNetwork);
            socialNetwork.register(newUser, new SimpleBoard());
            userThreads[i] = newUser;
            userThreads[i].start();
        }

        for (int i = 0; i < userCount; i++) {
            try {
                userThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (backlog.numberOfTasksInTheBacklog() != 0) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < workerCount; i++) {
            workerThreads[i].interrupt();
        }
        for (int i = 0; i < workerCount; i++) {
            try {
                workerThreads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}