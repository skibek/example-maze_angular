package org.example.maze.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.maze.domain.MazeInfo;
import org.example.maze.model.Maze;
import org.example.maze.model.MazeSolutionMode;
import org.example.maze.model.MazeSolverEnum;
import org.example.maze.web.rest.vm.InFileVM;
import org.example.maze.web.rest.vm.OutMazeFileVM;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileService {

    private final MazeSolverService mazeSolverService;

    public OutMazeFileVM consumeMazeFile(InFileVM inFileMetadata, MultipartFile multipartFile) throws IOException {
        String mazeBody = new String(multipartFile.getBytes());
        return processMaze(mazeBody, inFileMetadata);
    }

    public OutMazeFileVM readLocalFile(File fileWithMaze) throws FileNotFoundException {
        StringBuilder fileText = new StringBuilder();
        try (Scanner input = new Scanner(fileWithMaze)) {
            while (input.hasNextLine()) {
                fileText.append(input.nextLine()).append("\n");
            }
        }
        return processMaze(fileText.toString(),
                InFileVM.builder()
                        .mode(MazeSolutionMode.ALL_SOLUTIONS)
                        .fileName("test.only")
                        .build());
    }

    private OutMazeFileVM processMaze(String mazeBody, InFileVM inFileMetadata) {
        MazeInfo mazeInfo = mazeSolverService.solveMazeAndSave(MazeSolverEnum.Breadth_First_Search, mazeBody, inFileMetadata.getMode());
        mazeInfo.setCreateDate(new Date());
        mazeInfo.setMazeInString(mazeBody);
        mazeInfo.setFileName(inFileMetadata.getFileName());
        UUID uuid = UUID.randomUUID();
        mazeInfo.setUuid(uuid);

        return OutMazeFileVM.builder()
                .fileName(inFileMetadata.getFileName())
                .mazeInfo(mazeInfo)
                .uuid(uuid)
                .build();
    }
}
