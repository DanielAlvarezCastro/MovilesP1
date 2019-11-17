package com.engine;

public interface GameState {
        void init();
        void update(double deltaTime);
        void render(double deltaTime);
}
