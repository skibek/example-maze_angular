package org.example.maze.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.maze.model.Maze;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class MazeInfo {
    private Date createDate;
    private String fileName;
    private UUID uuid;
    private String mazeInString;
    private List<MazeInfoPath> mazeInfoPaths = new ArrayList<>();
    @JsonIgnore
    private Maze maze;
}
