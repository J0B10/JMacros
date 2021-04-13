package io.github.joblo2213.JMacros.core;

import io.github.joblo2213.JMacros.api.Action;
import io.github.joblo2213.JMacros.core.config.MacroData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The thread pool executor that does execute all macro actions.
 * <p>
 * It has a core pool size of one thread which will be enough for most macros that terminate quickly.
 * But some rarer macros will not finish immediately.
 * In that case the executor will create additional threads as needed and remove them when there is no more use for them.
 */
public class ActionsExecutor extends ThreadPoolExecutor {

    private static final String THREAD_PREFIX = "actions-pool-thread-";

    public static final Logger logger = LoggerFactory.getLogger(ActionsExecutor.class);

    public ActionsExecutor() {
        super(1, Integer.MAX_VALUE, 5, TimeUnit.SECONDS, new SynchronousQueue<>(), new PoolThreadFactory());
    }

    public void runActions(MacroData macro) {
       submit(new ActionsRunner(macro.getActions()));
    }

    private static class PoolThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        PoolThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, THREAD_PREFIX + threadNumber.getAndIncrement());
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    private static class ActionsRunner implements Runnable {

        private final Iterable<Action> actions;

        public ActionsRunner(Iterable<Action> actions) {
            this.actions = actions;
        }

        @Override
        public void run() {
            for (Action action : actions) {
                String actionName = action.getClass().getSimpleName();
                try {
                    action.run();
                } catch (InterruptedException e) {
                    logger.debug("Action " + actionName + " was interrupted");
                    return;
                } catch (Exception e) {
                    logger.error("Exception while running macro action " + actionName + ": " + e.getMessage(), e);
                }
            }
        }
    }
}
