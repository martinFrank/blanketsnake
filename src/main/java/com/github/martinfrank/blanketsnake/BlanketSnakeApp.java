package com.github.martinfrank.blanketsnake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlanketSnakeApp extends Application implements ExecutorHook<SnakeModel> {

    private static final int CORE_POOL_SIZE = 1;
    private static final int MAXIMUM_POOL_SIZE = 1;
    private static final long KEEP_ALIVE_TIME = 1L;
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private static final Logger LOGGER = LoggerFactory.getLogger(BlanketSnakeApp.class);

    private final BlockingQueue<Runnable> pool = new LinkedBlockingQueue<>();

    private Button button;
    private Canvas canvas;

    private ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, pool);

    public static void main(String[] parameters) {
        launch(parameters);
    }

    @Override
    public void init() {
        button = new Button("shake my snake");
        button.setOnAction(event -> executeSnakeAnimation());
        canvas = new Canvas();
        canvas.setWidth(110);
        canvas.setHeight(30);
    }

    private void executeSnakeAnimation() {
        if (threadPoolExecutor.getActiveCount() == 0) {
            threadPoolExecutor.execute(new BlanketSnakeTask(this));
        } else {
            LOGGER.debug("still busy, skipping request");
        }
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.getChildren().add(button);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setTitle("tbd title");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        threadPoolExecutor.shutdown();
    }

    @Override
    public void hook(SnakeModel snakeModel) {
        snakeModel.drawOnJFx(canvas.getGraphicsContext2D());

    }
}
