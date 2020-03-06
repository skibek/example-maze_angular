package org.example.maze.model;

public enum MazeStructureEnum {
    PATH(".", '1'),
    WALL("#", '0'),
    IN("I", ' '),
    OUT("O", ' '),
    SUCCESS_PATH("+", ' ');

    private String graphics;
    private Character inputChar;

    MazeStructureEnum(String graphics, Character inputChar) {
        this.graphics = graphics;
        this.inputChar = inputChar;
    }

    public String getGraphics() {
        return graphics;
    }

    public Character getInputChar() {
        return inputChar;
    }
}
