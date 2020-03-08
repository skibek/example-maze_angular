package org.example.maze.model;

/**
 * Directions for moving on maze
 */
public enum DirectionEnum {
    UP(new Point(0, 1)),
    RIGHT(new Point(1, 0)),
    DOWN(new Point(0, -1)),
    LEFT(new Point(-1, 0));

    private Point point;

    DirectionEnum(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }
}
