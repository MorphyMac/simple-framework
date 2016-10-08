package com.tylersuehr.concurrent;
import com.tylersuehr.Log;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * Copyright Tyler Suehr 2016
 * Created by tyler
 *
 * This is to simplify multi-threading. A task can run work in the background and
 * when complete, run on the main thread afterwards. The mechanism that allows us to
 * run/spawn new threads is a fixed thread pool.
 *
 * This object will call the following methods accordingly:
 *      1. {@link #onPreExecute()} main thread
 *      2. {@link #doInBackground()} worker thread
 *      3. {@link #onPostExecute(Object)} main thread
 *
 *
 * You can register observers to the {@link Task} class to always be updated when
 * the a task completes. Observers are static so when the task completes, it doesn't
 * reset the observer. NOTE: You will need to manually remove observers.
 *
 * You can also register an callback to listener for execution states. {@link OnTaskStateListener}
 * will allow you to receive callbacks during the calling of the {@link #onPreExecute()} and
 * {@link #onPostExecute(Object)} methods.
 *
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
 * -----------------------------------------------------------------
 */
public abstract class Task<T> {
    private final static ExecutorService executor = Executors.newFixedThreadPool(2); // thread pool
    private final static Vector<IObserver> observers = new Vector<>(); // stores our observers

    private final FutureTask<T> futureTask; // main task to run
    private volatile boolean invoked = false; // to determine if task was already run
    private volatile boolean running = false; // to determine when new thread is running
    private OnTaskStateListener stateListener;


    public Task() {
        // Create the task that will run in worker thread
        final Callable<T> workerTask = new Callable<T>() {
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
        if (stateListener != null) {
            stateListener.onPreExecute();
        }
    }

    /**
     * Called on main thread after {@link #doInBackground()} is called.
     * @param data Generic type T
     */
    protected void onPostExecute(T data) {
        if (stateListener != null) {
            stateListener.onPostExecute();
        }
        notifyObservers(data);
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

    public void setStateListener(OnTaskStateListener callback) {
        this.stateListener = callback;
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

    /**
     * Alert all observers that the data has been updated.
     * @param data Data
     */
    private void notifyObservers(T data) {
        final Object[] obs;

        synchronized (this) {
            obs = observers.toArray();
        }

        for (int i = observers.size() - 1; i >= 0; i--) {
            ((IObserver)obs[i]).onUpdate(data);
        }
    }


    /**
     * Register an observer to our vector.
     * @param observer {@link IObserver}
     */
    public static void register(IObserver observer) {
        observers.addElement(observer);
    }

    /**
     * Unregister an observer from our vector.
     * @param observer {@link IObserver}
     */
    public static void unregister(IObserver observer) {
        observers.removeElement(observer);
    }

    /**
     * Unregister any observers in our vector.
     */
    public static void unregisterAll() {
        observers.removeAllElements();
    }
}