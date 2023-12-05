package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game;

import javafx.scene.paint.Color;

public class Config {
    public static final GameConfig GAME;
    public static final ThemeConfig THEME;

    static {
        GAME = new GameConfig();
        THEME = new ThemeConfig();
    }

    public static class GameConfig {
        private static final double DEFAULT_WINDOW_WIDTH = 1280;
        private static final double DEFAULT_WINDOW_HEIGHT = 720;
        private static final double DEFAULT_PLAYER_SIZE = 20;
        private static final double DEFAULT_APPLE_SIZE = 10;
        private static final double DEFAULT_PLAYER_SPEED = 2;
        private static final Direction DEFAULT_START_DIRECTION = Direction.RIGHT;
        private static final double DEFAULT_PLAYER_START_X = DEFAULT_WINDOW_WIDTH / 4.0 - DEFAULT_PLAYER_SIZE / 2.0;
        private static final double DEFAULT_PLAYER_START_Y = (DEFAULT_WINDOW_HEIGHT - DEFAULT_PLAYER_SIZE) / 2.0;

        private final double windowWidth;
        private final double windowHeight;
        private final double playerSize;
        private final double appleSize;
        private final double playerSpeed;
        private final Direction startDirection;
        private final double playerStartX;
        private final double playerStartY;

        private GameConfig() {
            this(DEFAULT_WINDOW_WIDTH,
                    DEFAULT_WINDOW_HEIGHT,
                    DEFAULT_PLAYER_SIZE,
                    DEFAULT_APPLE_SIZE,
                    DEFAULT_PLAYER_SPEED,
                    DEFAULT_START_DIRECTION,
                    DEFAULT_PLAYER_START_X,
                    DEFAULT_PLAYER_START_Y);
        }

        public GameConfig(double windowWidth, double windowHeight, double playerSize, double appleSize, double playerSpeed, Direction startDirection, double playerStartX, double playerStartY) {
            this.windowWidth = windowWidth;
            this.windowHeight = windowHeight;
            this.playerSize = playerSize;
            this.appleSize = appleSize;
            this.playerSpeed = playerSpeed;
            this.startDirection = startDirection;
            this.playerStartX = playerStartX;
            this.playerStartY = playerStartY;
        }

        public double getWindowWidth() {
            return windowWidth;
        }

        public double getWindowHeight() {
            return windowHeight;
        }

        public double getPlayerSize() {
            return playerSize;
        }

        public double getAppleSize() {
            return appleSize;
        }

        public double getPlayerSpeed() {
            return playerSpeed;
        }

        public Direction getStartDirection() {
            return startDirection;
        }

        public double getPlayerStartX() {
            return playerStartX;
        }

        public double getPlayerStartY() {
            return playerStartY;
        }
    }

    public static class ThemeConfig {
        public static final Color DEFAULT_BACKGROUND = Color.web("#FCF5ED");
        public static final Color DEFAULT_PLAYER = Color.web("#1F1717");
        public static final Color DEFAULT_APPLE = Color.web("#CE5A67");
        public static final Color DEFAULT_TIMER = Color.web("#CE5A67");

        private final Color background;
        private final Color player;
        private final Color apple;
        private final Color timer;

        public ThemeConfig() {
            this.background = DEFAULT_BACKGROUND;
            this.player = DEFAULT_PLAYER;
            this.apple = DEFAULT_APPLE;
            this.timer = DEFAULT_TIMER;
        }

        public ThemeConfig(Color background, Color player, Color apple, Color timer) {
            this.background = background;
            this.player = player;
            this.apple = apple;
            this.timer = timer;
        }

        public Color getBackground() {
            return background;
        }

        public Color getPlayer() {
            return player;
        }

        public Color getApple() {
            return apple;
        }

        public Color getTimer() {
            return timer;
        }
    }
}
