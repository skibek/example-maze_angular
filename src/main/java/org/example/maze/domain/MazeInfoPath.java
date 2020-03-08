package org.example.maze.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.example.maze.model.Point;

import java.util.List;

@Builder
@Data
@ToString
public class MazeInfoPath {
    private List<Point> succesfulPathList;
    private int counterChangeDirection;
    private String mazeWithResult;
}
