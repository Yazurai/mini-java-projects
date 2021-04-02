package socialnetwork.domain;

import java.util.Optional;

public interface Backlog {
    public boolean add(Task task);
    public Optional<Task> getNextTaskToProcess();
    public int numberOfTasksInTheBacklog();
}
