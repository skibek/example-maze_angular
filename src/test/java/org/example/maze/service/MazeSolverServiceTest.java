package org.example.maze.service;

import lombok.extern.slf4j.Slf4j;
import org.example.maze.MazeApplication;
import org.example.maze.domain.MazeInfo;
import org.example.maze.model.Maze;
import org.example.maze.model.MazeSolverEnum;
import org.example.maze.model.Point;
import org.example.maze.service.tools.MazeTools;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest(classes = MazeApplication.class)
public class MazeSolverServiceTest {

    private static String ls = System.lineSeparator();
    private static String mazeInString;

    @Autowired
    private MazeSolverService mazeSolverService;

    @BeforeAll
    public static void setup() {
        mazeInString = "9,8" + ls
                + "000000000" + ls
                + "111101110" + ls
                + "010101010" + ls
                + "010111010" + ls
                + "010000010" + ls
                + "011110010" + ls
                + "010011111" + ls
                + "000000000";
    }

    @Test
    public void test() {
        MazeTools mazeTools = new MazeTools();
        Maze maze = mazeTools.initializeMaze(mazeInString);
        log.info(maze.toString());
        log.info(maze.displayGraphics());

        MazeInfo mazeInfo = mazeSolverService.solveMaze(MazeSolverEnum.Breadth_First_Search, maze);
        //log.info(list.toString());
        for (List<Point> list : mazeInfo.getSuccesfulPathList()) {
            maze = mazeTools.initializeMaze(mazeInString);
            mazeTools.placeRouteOnMaze(maze, list);
            log.info(maze.displayGraphics());
        }
        //mazeInfo.getSuccesfulPathList().forEach(list -> mazeTools.placeRouteOnMaze(maze, list));


        //TODO - test two algorithms
    }

    @Test
    public void test2() {

    }
}
