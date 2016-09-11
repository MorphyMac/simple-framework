package com.tylersuehr.concurrent;
import com.tylersuehr.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This is to simplify the multi-threading. A task can run work in the background and
 * when complete, run on the main thread afterwards.
 *
 * This object will call the following methods accordingly:
 *      1. {@link #onPreExecute()} main thread
 *      2. {@link #doInBackground()} new thread
 *      3. {@link #onPostExecute(Object)} main thread
 *
 * The mechanism that allows us to run/spawn new threads is {@link Executor}. We have
 * created a few executors that function differently accordingly:
 *      1. { SingleExecutor} spawns one new thread to run one single task
 *      1. { SerialExecutor} spawns one new thread and runs all tasks on it
 *      2. { ParallelExecutor} spawns a new thread to run each task in parallel
 *
 * The default executor used by this class is { SingleExecutor}.
 *
 * You can also register an callback, { ITaskObserver}, that will allow you
 * to receive callbacks during the calling of the {@link #onPreExecute()} and
 * {@link #onPostExecute(Object)} methods.
 *
 * Here is a simple example implementation of this object:
 * --------------------------------------------------------------
 * class FindUsersTask extends Task<List<User>> {
 *     @Override
 *     protected List<User> doInBackground() {
 *          Repository repo = new Repository();
 *          List<User> users = repo.findAllUsers();
 *          return users;
 *     }
 *
 *     @Override
 *     protected void onPostExecute(List<User> users) {
 *          if (users != null) {
 *              for (User user : users) {
 *                  displayUser(user);
 *              }
 *          }
 *     }
 * }
 *
 * public void main(String[] args) {
 *     FindUsersTask task = new FindUsersTask();
 *     task.execute();
 * }
 * ----------------------------------------------------------------
 *
 *
 * Here is another example using the ability of an executor itself:
 * -----------------------------------------------------------------
 * public void main(String[] args) {
 *      Collection<Task> tasks = new ArrayList<>();
 *      tasks.add(new GetFeedTask());
 *      tasks.add(new GetTweetsTask());
 *      tasks.add(new GetReTweetsTask());
 *
 *      ParallelExecutor executor = new ParallelExecutor();
 *      for (Task task : tasks) {
 *          executor.add(task.getRunnableTask());
 *      }
 *      executor.execute();
 * }
 * -----------------------------------------------------------------
 */
public abstract class Task<T> {
    private final Callable<T> workerTask;
    private final FutureTask<T> futureTask;
    private volatile boolean invoked = false; // to determine if task was already run
    private volatile boolean running = false; // to determine when new thread is running
    private Executor executor = new SingleExecutor();
    private ITaskCallback callback;


    public Task() {
        // Create the task that will run in new thread
        workerTask = new Callable<T>() {
            @Override
            public T call() throws Exception {
                // Make sure we know task is running
                running = true;

                // Run task
                return doInBackground();
            }
        };

        // Create the task that will run when worker finishes
        futureTask = new FutureTask<T>(workerTask) {
            @Override
            protected void done() {
                // Make sure we know task isn't running anymore
                running = false;

                // Try to get result from worker task
                try {
                    onPostExecute(get());
                } catch (ExecutionException|InterruptedException ex) {
                    Log.e("TASK", "Couldn't execute onPostExecute()!", ex);
                }
            }
        };
    }

    /**
     * Called on new thread to perform heavy tasks.
     * @return Generic type T
     */
    protected  abstract T doInBackground();

    /**
     * Called on main thread right before {@link #doInBackground()} is called.
     */
    protected void onPreExecute() {
        if (callback != null) {
            callback.onPreExecute();
        }
    }

    /**
     * Called on main thread after {@link #doInBackground()} is called.
     * @param data Generic type T
     */
    protected void onPostExecute(T data) {
        if (callback != null) {
            callback.onPostExecute(data);
        }
    }

    /**
     * Executes the current task.
     */
    public void execute() {
        if (invoked) {
            throw new IllegalStateException("Task already invoked!");
        }
        invoked = true;
        onPreExecute();
        executor.execute(futureTask);
    }

    /**
     * Gets the runnable task itself.
     * NOTE: Assumes you are using this to execute task, so we
     *       set the {@link #invoked} value to true.
     * @return {@link FutureTask<T>}
     */
    public FutureTask<T> getRunnableTask() {
        invoked = true;
        return futureTask;
    }

    public void setCallback(ITaskCallback callback) {
        this.callback = callback;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public boolean isInvoked() {
        return invoked;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isCancelled() {
        return futureTask.isCancelled();
    }

    public void cancel(boolean interrupt) {
        futureTask.cancel(interrupt);
    }
}