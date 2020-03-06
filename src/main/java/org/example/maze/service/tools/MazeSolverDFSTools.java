package org.example.maze.service.tools;

import lombok.extern.slf4j.Slf4j;
import org.example.maze.domain.MazeInfo;
import org.example.maze.model.DirectionEnum;
import org.example.maze.model.Maze;
import org.example.maze.model.MazeStructureEnum;
import org.example.maze.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class MazeSolverDFSTools {

    public MazeInfo solve_DepthFirst(Maze maze) {
        MazeInfo mazeInfo = new MazeInfo();
        List<Point> path = new ArrayList<>();
        if (explore(maze, maze.getIn(), path, mazeInfo)) {
            return mazeInfo;
        } else {
            return MazeInfo.builder().succesfulPath(Collections.emptyList()).build();
        }
    }

    private boolean explore(Maze maze, Point entry, List<Point> path, MazeInfo mazeInfo) {
        if (!maze.isValidLocation(entry)
            || maze.getStructureByPoint(entry) == MazeStructureEnum.WALL
            || maze.isExplored(entry) ) {
            return false;
        }

        path.add(entry);
        maze.setVisited(entry, true);

        if (maze.getStructureByPoint(entry) == MazeStructureEnum.OUT) {
            mazeInfo.getSuccesfulPathList().add(path);
            return true;
        }

        for (DirectionEnum direction : DirectionEnum.values()) {
            Point nextPoint = MazeTools.getNextMove(entry, direction.getPoint());
            if (explore(maze, nextPoint, path, mazeInfo)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }
}
