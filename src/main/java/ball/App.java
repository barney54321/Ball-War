package ball;

import ball.controller.Scene;
import processing.core.PApplet;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 220;

    private Scene game;

    public App() {
        this.game = new Scene(this);
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        frameRate(FPS);
    }

    public void draw() {
        background(0, 0, 0);
        this.game.update();
    }

    public void keyPressed() {
        if (keyCode == 82) {
            this.game = new Scene(this);
        }
    }

    public static void main(String[] args) {
        PApplet.main("ball.App");
    }
}
