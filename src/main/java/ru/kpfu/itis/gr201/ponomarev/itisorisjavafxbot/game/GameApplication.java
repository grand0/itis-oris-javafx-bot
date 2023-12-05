package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.ui.GameField;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.ui.PointsCounter;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.ui.Timer;

import java.util.HashSet;
import java.util.Set;

public class GameApplication extends Application {

    private GameField gameField;
    private Timer timer;
    private PointsCounter points;
    private double playerSpeed;
    private Rectangle player;
    private Circle apple;
    private Circle poisonedApple;
    private Set<KeyCode> pressedKeys;
    private Direction direction;
    private Timeline gameLoop;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game");
        primaryStage.setOnCloseRequest(event -> Platform.exit());

        timer = new Timer();
        timer.setFont(Font.font("Consolas", 24));
        timer.setFill(Config.THEME.getTimer());
        timer.setCallback(() -> {
            if (timer.getSecondsPassed() % 10 == 0) {
                if (poisonedApple == null) {
                    poisonedApple = makePoisonedApple();
                } else {
                    poisonedApple = null;
                }
                gameField.replacePoisonedApple(poisonedApple);
            }
            if (timer.getSecondsPassed() % 30 == 0) {
                timer.playEmphasizeAnimation();
                increaseSpeed();
            }
        });
        StackPane.setMargin(timer, new Insets(10));
        StackPane.setAlignment(timer, Pos.TOP_CENTER);

        points = new PointsCounter();
        points.setFont(Font.font("Consolas", 144));
        points.setFill(Config.THEME.getTimer().deriveColor(0, 1, 1, 0.3));
        StackPane.setAlignment(points, Pos.CENTER);

        player = makePlayer();
        gameField = new GameField(player);

        StackPane root = new StackPane(gameField, points, timer);
        Scene scene = new Scene(root, Config.GAME.getWindowWidth(), Config.GAME.getWindowHeight());

        pressedKeys = new HashSet<>();
        scene.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode());
        });
        scene.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
        });

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        gameLoop = makeGameLoop();

        startGame();
    }

    private void startGame() {
        direction = Config.GAME.getStartDirection();
        playerSpeed = Config.GAME.getPlayerSpeed();

        player.setWidth(Config.GAME.getPlayerSize());
        player.setHeight(Config.GAME.getPlayerSize());
        player.setX(Config.GAME.getPlayerStartX());
        player.setY(Config.GAME.getPlayerStartY());
        player.setFill(Config.THEME.getPlayer());

        apple = makeApple();
        poisonedApple = null;
        gameField.replaceApple(apple);
        gameField.replacePoisonedApple(poisonedApple);

        points.reset();

        timer.stop();
        timer.start();

        gameLoop.playFromStart();
    }

    private void stopGame() {
        player.setFill(Config.THEME.getPlayer().deriveColor(0, 1, 1, 0.5));
        gameLoop.pause();
        timer.pause();
    }

    private void increaseSpeed() {
        playerSpeed += 1;
    }

    private Rectangle makePlayer() {
        Rectangle player = new Rectangle(Config.GAME.getPlayerStartX(),
                Config.GAME.getPlayerStartY(),
                Config.GAME.getPlayerSize(),
                Config.GAME.getPlayerSize());
        player.setFill(Config.THEME.getPlayer());
        return player;
    }

    private Circle makeApple() {
        Circle apple = generateApple();
        apple.setFill(Config.THEME.getApple());
        return apple;
    }

    private Circle makePoisonedApple() {
        Circle apple = generateApple();
        apple.setFill(Config.THEME.getPoisonedApple());
        return apple;
    }

    private Circle generateApple() {
        Circle apple = new Circle(Math.random() * (Config.GAME.getWindowWidth() - 40) + 20,
                Math.random() * (Config.GAME.getWindowHeight() - 40) + 20,
                Config.GAME.getAppleSize() / 2.0);
        while (Shape.intersect(player, apple).getBoundsInParent().getWidth() > 0) {
            apple = new Circle(Math.random() * (Config.GAME.getWindowWidth() - 40) + 20,
                    Math.random() * (Config.GAME.getWindowHeight() - 40) + 20,
                    Config.GAME.getAppleSize() / 2.0);
        }
        return apple;
    }

    private Timeline makeGameLoop() {
        Timeline gameLoop = new Timeline();
        KeyFrame kf = new KeyFrame(new Duration(1000 / 60.0), event -> {
            if (
                    player.getX() < 0
                    || player.getY() < 0
                    || player.getX() + player.getWidth() > Config.GAME.getWindowWidth()
                    || player.getY() + player.getHeight() > Config.GAME.getWindowHeight()
                    || (
                            poisonedApple != null
                            && Shape.intersect(player, poisonedApple).getBoundsInParent().getWidth() > 0
                    )
            ) {
                gameOver();
            }

            if (Shape.intersect(player, apple).getBoundsInParent().getWidth() > 0) {
                apple = makeApple();
                gameField.replaceApple(apple);
                player.setWidth(player.getWidth() + 5);
                player.setHeight(player.getHeight() + 5);
                points.increment();
            }

            if (pressedKeys.contains(KeyCode.W)) {
                direction = Direction.UP;
            }
            if (pressedKeys.contains(KeyCode.S)) {
                direction = Direction.DOWN;
            }
            if (pressedKeys.contains(KeyCode.A)) {
                direction = Direction.LEFT;
            }
            if (pressedKeys.contains(KeyCode.D)) {
                direction = Direction.RIGHT;
            }
            switch (direction) {
                case UP -> player.setY(player.getY() - playerSpeed);
                case DOWN -> player.setY(player.getY() + playerSpeed);
                case LEFT -> player.setX(player.getX() - playerSpeed);
                case RIGHT -> player.setX(player.getX() + playerSpeed);
            }
        });
        gameLoop.getKeyFrames().add(kf);
        gameLoop.setCycleCount(Animation.INDEFINITE);
        return gameLoop;
    }

    private void gameOver() {
        stopGame();
        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(
                Duration.seconds(3),
                event -> {
                    startGame();
                }
        );
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}
