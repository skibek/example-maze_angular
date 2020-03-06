package org.example.maze.service.tools;

import org.example.maze.model.DirectionEnum;
import org.example.maze.model.Maze;
import org.example.maze.model.MazeStructureEnum;
import org.example.maze.model.Point;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class MazeTools {

    public Maze initByFile(File maze) throws FileNotFoundException {
        String fileText = "";
        try (Scanner input = new Scanner(maze)) {
            while (input.hasNextLine()) {
                fileText += input.nextLine() + "\n";
            }
        }
        return initializeMaze(fileText);
    }

    public Maze initializeMaze(String text) {
        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("empty lines data");
        }

        String[] initLines = text.split("[\r]?\n");
        String mazeSizeStr = initLines[0];
        String[] mazeSizeArr = mazeSizeStr.split(",");
        if (mazeSizeArr.length != 2) {
            throw new IllegalArgumentException("maze size not ok");
        }
        String[] lines = new String[initLines.length-1];
        System.arraycopy(initLines, 1, lines, 0, lines.length);

        int width = Integer.parseInt(mazeSizeArr[0]);
        int height = Integer.parseInt(mazeSizeArr[1]);
        Maze maze = Maze.builder()
            .width(width)
            .height(Integer.parseInt(mazeSizeArr[1]))
            .structure(new MazeStructureEnum[height][width])
            .visited(new boolean[height][width])
            .build();

        for (int row = 0; row < height; row++) {
            if (lines[row].length() != width) {
                throw new IllegalArgumentException("line " + (row + 1) + " wrong length (was " + lines[row].length() + " but should be " + width+ ")");
            }

            for (int col = 0; col < width; col++) {
                Point point = new Point(row, col);
                if (lines[row].charAt(col) == MazeStructureEnum.WALL.getInputChar()) {
                    maze.setStructureByPoint(point, MazeStructureEnum.WALL);
                } else if (lines[row].charAt(col) == MazeStructureEnum.PATH.getInputChar()) {
                    maze.setStructureByPoint(point, MazeStructureEnum.PATH);

                    if (col == 0) {
                        maze.setInPoint(row, 0);
                    } else if (row == 0) {
                        maze.setInPoint(0, col);
                    } else if (col == width - 1) {
                        maze.setOutPoint(row, width - 1);
                    } else if (row == height - 1) {
                        maze.setOutPoint(height-1, col);
                    }
                }
            }
        }
        return maze;
    }

    public void placeRouteOnMaze(Maze maze, List<Point> listRoute) {
        listRoute.forEach(routePoint -> maze.setStructureByPoint(routePoint, MazeStructureEnum.SUCCESS_PATH));
    }

    public static Point getNextMove(Point entry, Point direction) {
        return new Point(entry.getX() + direction.getX(), entry.getY() + direction.getY());
    }
}
