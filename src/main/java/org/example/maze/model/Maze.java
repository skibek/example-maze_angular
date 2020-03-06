package org.example.maze.model;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
@Builder
public class Maze {

    private MazeStructureEnum[][] structure;
    private boolean[][] visited;
    private Point in;
    private Point out;
    private int width;
    private int height;

    public void setInPoint(int x, int y) {
        in = new Point(x, y);
        structure[x][y] = MazeStructureEnum.IN;
    }

    public void setOutPoint(int x, int y) {
        out = new Point(x, y);
        structure[x][y] = MazeStructureEnum.OUT;
    }

    public MazeStructureEnum getStructureByPoint(Point point) {
        return structure[point.getX()][point.getY()];
    }

    public void setStructureByPoint(Point point, MazeStructureEnum mazeStructureEnum) {
        structure[point.getX()][point.getY()] = mazeStructureEnum;
    }

    public boolean isExplored(Point point) {
        return visited[point.getX()][point.getY()];
    }

    public void setVisited(Point point, boolean value) {
        visited[point.getX()][point.getY()] = value;
    }

    public boolean isValidLocation(Point point) {
        if (point.getX() < 0 || point.getX() >= height || point.getY() < 0 || point.getY() >= width) {
            return false;
        }
        return true;
    }

    public String displayGraphics() {
        StringBuilder result = new StringBuilder();
        result.append(System.lineSeparator());
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                result.append(structure[row][col].getGraphics());
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    public void reset() {
        for (int i = 0; i < visited.length; i++)
            Arrays.fill(visited[i], false);
    }
}
