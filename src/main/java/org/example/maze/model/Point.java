package org.example.maze.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Point implements Cloneable {

    private int x;
    private int y;
    Point parent;
    DirectionEnum direction;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isTheSamePoint(Point toCheck) {
        return toCheck.getX() == x && toCheck.getY() == y;
    }
}
