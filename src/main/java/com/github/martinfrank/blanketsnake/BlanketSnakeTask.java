package com.github.martinfrank.blanketsnake;

import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BlanketSnakeTask extends Task<SnakeModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlanketSnakeTask.class);
    private final ExecutorHook<SnakeModel> hook;
    private final SnakeModel snakeModel;


    BlanketSnakeTask(ExecutorHook<SnakeModel> executorHook) {
        this.hook = executorHook;
        snakeModel = new SnakeModel();
    }

    @Override
    protected SnakeModel call() {
        IntStream.range(0, 10).forEach(this::iterate);
        String msg = "Done! ";
        LOGGER.debug(msg);
        updateMessage(msg);
        succeeded();
        return snakeModel;
    }

    private void iterate(int i) {
        String msg = "processing...";
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.debug(msg);
        updateMessage(msg);
        snakeModel.iterate(i);
        hook.hook(snakeModel);
    }


}
