package org.example.maze.service;

import lombok.extern.slf4j.Slf4j;
import org.example.maze.domain.MazeInfoPath;
import org.example.maze.model.Maze;
import org.example.maze.service.tools.MazeTools;
import org.example.maze.web.rest.vm.OutMazeFileVM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class FileServiceTest {

    private static final String pathToProject = System.getProperty("user.dir");
    private static final String pathToMazes = String.format("%s\\%s", pathToProject, "doc\\mazes\\");

    @Autowired
    private MazeTools mazeTools;

    @Autowired
    private FileService fileService;

    @Test
    public void testReadLocalFile() throws FileNotFoundException {
        //given
        File file = new File(pathToMazes + "maze1.txt");
        //when
        OutMazeFileVM outMazeFileVM = fileService.readLocalFile(file);
        showSolution(outMazeFileVM);
        //then
        assertThat(outMazeFileVM.getUuid()).isNotNull();
    }

    @Test
    public void testReadLocalFile2() throws FileNotFoundException {
        //given
        File file = new File(pathToMazes + "maze2.txt");
        //when
        OutMazeFileVM outMazeFileVM = fileService.readLocalFile(file);
        showSolution(outMazeFileVM);
        //then
        assertThat(outMazeFileVM.getUuid()).isNotNull();
    }

    @Test
    public void testReadLocalFileError() {
        //given
        File file = new File(pathToMazes + "mazeError.txt");
        //when - then
        Assertions.assertThrows(IllegalArgumentException.class, () -> fileService.readLocalFile(file));
    }

    private void showSolution(OutMazeFileVM outMazeFileVM) {
        Maze maze = mazeTools.initializeMaze(outMazeFileVM.getMazeInfo().getMazeInString());
        for (MazeInfoPath mazeInfoPath : outMazeFileVM.getMazeInfo().getMazeInfoPaths()) {
            mazeTools.placeRouteOnMaze(maze, mazeInfoPath.getSuccesfulPathList(), false);
            log.info(maze.displayGraphics());
            log.info("getCounterChangeDirection=" + mazeInfoPath.getCounterChangeDirection());
            mazeTools.placeRouteOnMaze(maze, mazeInfoPath.getSuccesfulPathList(), true);
        }
    }
}
