package socialnetwork.domain;

import java.util.Optional;

public class Worker extends Thread {

    private final Backlog backlog;
    private boolean interrupted;

    public Worker(Backlog backlog) {
        this.backlog = backlog;
        interrupted = false;
    }

    @Override
    public void run() {
        while (!interrupted) {
            try {
                Optional<Task> opTask = backlog.getNextTaskToProcess();
                if (opTask.isPresent()) {
                    if (opTask.get().getCommand().equals(Task.Command.DELETE)) {
                        if (!opTask.get().getBoard().deleteMessage(opTask.get().getMessage())) {
                            backlog.add(opTask.get());
                        }
                    } else {
                        opTask.get().getBoard().addMessage(opTask.get().getMessage());
                    }
                } else {
                    Thread.sleep(25);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void interrupt() {
        this.interrupted = true;
    }

    public void process(Task nextTask) {
        // implement here
    }
}
