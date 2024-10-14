package ball.object;

import ball.App;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ball extends GameObject {
    public static final int VEL_MULTIPLIER = 2;

    private boolean white;
    private int xVel;
    private int yVel;

    public Ball(int x, int y, int radius, boolean white) {
        super(x, y, radius, radius);
        this.white = white;

        this.xVel = new Random().nextBoolean() ? 1 : -1;
        this.yVel = new Random().nextBoolean() ? 1 : -1;
    }

    @Override
    public void draw(PApplet app) {
        app.ellipseMode(PConstants.RADIUS);
        app.fill(white ? 256 : 0, white ? 256 : 0, white ? 256 : 0);
        app.stroke(white ? 256 : 0, white ? 256 : 0, white ? 256 : 0);
        app.ellipse(this.x, this.y, this.width, this.width);
    }

    public void update(List<Block> blocks) {
        this.x += VEL_MULTIPLIER * this.xVel;
        this.y += VEL_MULTIPLIER * this.yVel;

        boolean updateX = false;
        boolean updateY = false;

        if (this.x - this.width <= 0 || this.x + this.width >= App.WIDTH) {
            updateX = true;
        }

        if (this.y - this.width <= 0 || this.y + this.height >= App.HEIGHT) {
            updateY = true;
        }

        List<Block> candidateBlocks = new ArrayList<>();

        for (Block block : blocks) {
            if (block.isWhite() == this.white) {
                if (distanceFromBall(this, block) < this.width) {
                    candidateBlocks.add(block);

                    double[] closestPoints = closestPoint(this, block);
                    if (
                        Math.abs(closestPoints[0] - this.x) > Math.abs(closestPoints[1] - this.y)
                    ) {
                        updateX = true;
                    } else {
                        updateY = true;
                    }
                }
            }
        }

        candidateBlocks.stream()
            .min((a, b) -> (int) (distanceFromBall(this, a) - distanceFromBall(this, b)))
            .ifPresent(Block::queueFlip);

        if (updateX) {
            this.xVel *= -1;
        }

        if (updateY) {
            this.yVel *= -1;
        }
    }

    static double distanceFromBall(Ball ball, Block block) {
        double[] closestPoints = closestPoint(ball, block);
        double distanceX = ball.x - closestPoints[0];
        double distanceY = ball.y - closestPoints[1];
        double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        return Math.sqrt(distanceSquared);
    }

    static double[] closestPoint(Ball ball, Block block) {
        double closestX = Math.max(block.getX(), Math.min(ball.x, block.getX() + block.getWidth()));
        double closestY = Math.max(block.getY(), Math.min(ball.y, block.getY() + block.getHeight()));
        return new double[] {closestX, closestY};
    }
}
