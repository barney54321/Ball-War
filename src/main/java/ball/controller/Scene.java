package ball.controller;

import ball.App;
import ball.object.Ball;
import ball.object.Block;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scene {
    private List<Ball> balls;
    private List<Block> blocks;
    private PApplet app;

    public Scene(PApplet app) {
        this.app = app;

        this.balls = List.of(
            new Ball(App.WIDTH / 2, 20, 12, true),
            new Ball(App.WIDTH / 2, App.WIDTH - 40, 12, false)
        );

        this.blocks = new ArrayList<>();

        int blockDim = 30;
        int numBlocks = (App.WIDTH * App.HEIGHT) / (blockDim * blockDim);
        int numBlocksStartingBlack = (
            (numBlocks / 2) +
            (new Random().nextInt(numBlocks / 2) - numBlocks / 4)
        );

        for (int y = 0; y < App.HEIGHT / blockDim; y++) {
            for (int x = 0; x < App.WIDTH / blockDim; x++) {
                this.blocks.add(new Block(x * blockDim, y * blockDim, blockDim, blockDim, this.blocks.size() > numBlocksStartingBlack));
            }
        }
    }

    public void update() {
        for (Ball ball : balls) {
            ball.update(blocks);
        }
        blocks.forEach(Block::flipIfNeeded);

        blocks.forEach(block -> block.draw(app));
        balls.forEach(ball -> ball.draw(app));
    }
}
