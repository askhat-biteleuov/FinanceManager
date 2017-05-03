package com.fm.internal.currency.scheduler;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GetCurrencyExecutor {

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private GetCurrencyTask currencyTask;
    private volatile ScheduledFuture<?> scheduledTask = null;

    private final int targetHour;
    private final int targetMin;
    private final int targetSec;

    private volatile boolean isBusy = false;

    public GetCurrencyExecutor(GetCurrencyTask currencyTask,
                               int targetHour,
                               int targetMin,
                               int targetSec) {
        this.currencyTask = currencyTask;
        this.targetHour = targetHour;
        this.targetMin = targetMin;
        this.targetSec = targetSec;
    }

    public void start() {
        scheduleNextTask(doTask());
    }

    private Runnable doTask() {
        return () -> {
            try {
                isBusy = true;
                currencyTask.execute();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                isBusy = false;
            }
            scheduleNextTask(doTask());
        };
    }

    private void scheduleNextTask(Runnable task) {
        long delay = computeNextDelay(targetHour, targetMin, targetSec);
        scheduledTask = executorService.schedule(task, delay, TimeUnit.SECONDS);
    }

    private static long computeNextDelay(int targetHour, int targetMin, int targetSec) {
        ZonedDateTime zonedNow = moscowDateTime();
        ZonedDateTime zonedNextTarget = zonedNow
                .withHour(targetHour)
                .withMinute(targetMin)
                .withSecond(targetSec)
                .withNano(0);

        if (zonedNow.compareTo(zonedNextTarget) > 0) {
            zonedNextTarget = zonedNextTarget.plusDays(1);
        }

        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        return duration.getSeconds();
    }

    private static ZonedDateTime moscowDateTime() {
        return ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
    }

    public void stop() {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
        executorService.shutdown();
        try {
            if (isBusy) {
                executorService.awaitTermination(1, TimeUnit.MINUTES);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
