package org.example.maze.web.rest.vm;

import lombok.Builder;
import lombok.Data;
import org.example.maze.domain.MazeInfo;

import java.util.UUID;

@Data
@Builder
public class OutMazeFileVM {
    private String fileName;
    private UUID uuid;
    private MazeInfo mazeInfo;
}
