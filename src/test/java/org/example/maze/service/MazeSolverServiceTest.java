package org.example.maze.service;

import lombok.extern.slf4j.Slf4j;
import org.example.maze.domain.MazeInfo;
import org.example.maze.domain.MazeInfoPath;
import org.example.maze.model.Maze;
import org.example.maze.model.MazeSolutionMode;
import org.example.maze.model.MazeSolverEnum;
import org.example.maze.service.tools.MazeTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class MazeSolverServiceTest {

    @Autowired
    private MazeTools mazeTools;

    @Autowired
    private MazeSolverService mazeSolverService;

    private static String mazeInString;
    private final int width = 9;
    private final int height = 8;

    @BeforeEach
    public void setup() {
        //given
        mazeInString = "" + width + "," + height + System.lineSeparator()
                + "000000000" + System.lineSeparator()
                + "111101110" + System.lineSeparator()
                + "010101010" + System.lineSeparator()
                + "010111010" + System.lineSeparator()
                + "010000010" + System.lineSeparator()
                + "011110010" + System.lineSeparator()
                + "010011111" + System.lineSeparator()
                + "000000000";
    }

    @Test
    public void testInitMaze() {
        //when
        Maze maze = mazeTools.initializeMaze(mazeInString);
        //then
        assertThat(maze.getWidth()).isEqualTo(width);
        assertThat(maze.getHeight()).isEqualTo(height);
        assertThat(maze.getIn()).isNotNull();
        assertThat(maze.getOut()).isNotNull();
    }

    @Test
    public void testBFS() {
        //when
        //log.info(maze.displayGraphics());
        MazeInfo mazeInfo = mazeSolverService.solveMaze(MazeSolverEnum.Breadth_First_Search, mazeInString);
        showSolution(mazeInfo);
        //then
        assertThat(mazeInfo.getMazeInfoPaths().size()).isEqualTo(2);
    }

    @Test
    public void testBFwithLeastMode() {
        //when
        MazeInfo mazeInfo = mazeSolverService.solveMazeAndSave(MazeSolverEnum.Breadth_First_Search, mazeInString, MazeSolutionMode.LEAST_CHANGES_OF_DIRECTION);
        //then
        assertThat(mazeInfo.getMazeInfoPaths().size()).isEqualTo(1);
        assertThat(mazeInfo.getMazeInfoPaths().get(0).getCounterChangeDirection()).isEqualTo(4);
    }

    @Test
    public void testDFS() {
        //when
        MazeInfo mazeInfo = mazeSolverService.solveMaze(MazeSolverEnum.Depth_First_Search, mazeInString);
        //then
        assertThat(mazeInfo.getMazeInfoPaths().size()).isEqualTo(1);
    }

    private void showSolution(MazeInfo mazeInfo) {
        Maze maze = mazeTools.initializeMaze(mazeInString);
        for (MazeInfoPath mazeInfoPath : mazeInfo.getMazeInfoPaths()) {
            mazeTools.placeRouteOnMaze(maze, mazeInfoPath.getSuccesfulPathList(), false);
            log.info(maze.displayGraphics());
            log.info("getCounterChangeDirection=" + mazeInfoPath.getCounterChangeDirection());
            mazeTools.placeRouteOnMaze(maze, mazeInfoPath.getSuccesfulPathList(), true);
        }
    }
}
