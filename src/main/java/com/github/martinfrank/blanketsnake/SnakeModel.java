package com.github.martinfrank.blanketsnake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

class SnakeModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnakeModel.class);

    private final Paint color;
    private int i = 0;

    SnakeModel() {
        Random random = new Random();
        color = Color.rgb(random.nextInt(0xFF), random.nextInt(0xFF), random.nextInt(0xFF));
    }

    void iterate(int i) {
        LOGGER.debug("next step: {}", i);
        this.i = i;
    }

    void drawOnJFx(GraphicsContext graphicsContext2D) {
        int x = (i * 10) + 10;
        int y = 10;
        int w = 8;
        int h = 8;
        graphicsContext2D.setFill(color);
        graphicsContext2D.fillRect(x, y, w, h);
    }
}
