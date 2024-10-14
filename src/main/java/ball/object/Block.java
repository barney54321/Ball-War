package ball.object;

import processing.core.PApplet;

public class Block extends GameObject {
    private boolean white;
    private boolean queued;

    public Block(int x, int y, int width, int height, boolean white) {
        super(x, y, width, height);
        this.white = white;
        this.queued = false;
    }

    @Override
    public void draw(PApplet app) {
        app.fill(white ? 256 : 0, white ? 256 : 0, white ? 256 : 0);
        app.stroke(white ? 256 : 0, white ? 256 : 0, white ? 256 : 0);
        app.rect(this.x, this.y, this.width, this.height);
    }

    public boolean isWhite() {
        return this.white;
    }

    public void queueFlip() {
        this.queued = true;
    }

    public void flipIfNeeded() {
        if (this.queued) {
            this.white = !this.white;
        }
        this.queued = false;
    }
}
