package com.engine;

public interface GameState {
        public void init();
        public void update(double deltaTime);
        public void render(double deltaTime);
}
