package org.example.maze.service.tools;

import lombok.extern.slf4j.Slf4j;
import org.example.maze.domain.MazeInfo;
import org.example.maze.domain.MazeInfoPath;
import org.example.maze.model.DirectionEnum;
import org.example.maze.model.Maze;
import org.example.maze.model.MazeStructureEnum;
import org.example.maze.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class MazeSolverBFSTools {

    public MazeInfo solve_BreadthFirst(Maze maze) {
        MazeInfo mazeInfo = new MazeInfo();
        mazeInfo.setMaze(maze);
        LinkedList<Point> nextToVisit = new LinkedList<>();
        Point start = maze.getIn();
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            Point next = nextToVisit.remove();

            if (!maze.isValidLocation(next) || maze.isExplored(next) ) {
                continue;
            }

            if (maze.getStructureByPoint(next) == MazeStructureEnum.WALL) {
                maze.setVisited(next);
                continue;
            }

            if (maze.getStructureByPoint(next) == MazeStructureEnum.OUT ) {
                mazeInfo.getMazeInfoPaths().add(getBacktrackPath(next));
            }

            for (DirectionEnum direction : DirectionEnum.values()) {
                Point point = new Point(
                        next.getX() + direction.getPoint().getX(),
                        next.getY() + direction.getPoint().getY(),
                        next,
                        direction
                );
                if (getBacktrackToCheckIfBeenThere(next, point)) {
                    nextToVisit.add(point);
                }
                //todo check if there was direction change

                //maze.setVisited(next, true);
            }
        }
        return mazeInfo;
    }

    private MazeInfoPath getBacktrackPath(Point cur) {
        List<Point> path = new ArrayList<>();
        int counter = 0;
        Point iter = cur;

        while (iter != null) {
            if (iter.getParent() != null && iter.getDirection() != iter.getParent().getDirection())
                counter++;
            path.add(iter);
            iter = iter.getParent();
        }
        return MazeInfoPath.builder().succesfulPathList(path).counterChangeDirection(counter-1).build();
    }

    private boolean getBacktrackToCheckIfBeenThere(Point cur, Point pointToCheck) {
        Point iter = cur;

        while (iter != null) {
            if (iter.isTheSamePoint(pointToCheck)) return false;
            iter = iter.getParent();
        }

        return true;
    }

}
