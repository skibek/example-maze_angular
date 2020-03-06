package org.example.maze.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.maze.domain.MazeInfo;
import org.example.maze.model.Maze;
import org.example.maze.model.MazeSolverEnum;
import org.example.maze.service.tools.MazeTools;
import org.example.maze.web.rest.vm.InFileVM;
import org.example.maze.web.rest.vm.OutMazeFileVM;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileService {

    private final RepoService repoService;
    private final MazeSolverService mazeSolverService;
    private final MazeTools mazeTools;

    public OutMazeFileVM consumeMazeFile(InFileVM fileMetadata, MultipartFile multipartFile) throws IOException {
        String mazeBody = new String(multipartFile.getBytes());

        MazeInfo mazeInfo = MazeInfo.builder()
                .createDate(new Date())
                .fileName(fileMetadata.getFileName())
                .uuid(UUID.randomUUID())
                .build();

        //todo go to calc and add info
        Maze maze = mazeTools.initializeMaze(mazeBody);
        log.info(maze.toString());
        log.info(maze.displayGraphics());
        mazeSolverService.solveMaze(MazeSolverEnum.Breadth_First_Search, maze);

        repoService.add(mazeInfo);

        return OutMazeFileVM.builder()
                .fileName(fileMetadata.getFileName())
                .mazeInfo(mazeInfo)
                .uuid(UUID.randomUUID())
                .build();
    }

    public void readLocalFile(String path) {

    }
}
