package org.example.maze.domain;

import lombok.*;
import org.example.maze.model.Point;

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
    private String fileName;
    private UUID uuid;
    private Date createDate;
    private List<Point> succesfulPath;
    private List<List<Point>> succesfulPathList = new ArrayList<>();
}
