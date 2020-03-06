package org.example.maze.service.tools;

import lombok.extern.slf4j.Slf4j;
import org.example.maze.domain.MazeInfo;
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
        LinkedList<Point> nextToVisit = new LinkedList<>();
        Point start = maze.getIn();
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            Point next = nextToVisit.remove();

            if (!maze.isValidLocation(next) || maze.isExplored(next) ) {
                continue;
            }

            if (maze.getStructureByPoint(next) == MazeStructureEnum.WALL) {
                maze.setVisited(next, true);
                continue;
            }

            if (maze.getStructureByPoint(next) == MazeStructureEnum.OUT ) {
                List<Point> listSuccess = getBacktrackPath(next);
                mazeInfo.getSuccesfulPathList().add(listSuccess);
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

    private List<Point> getBacktrackPath(Point cur) {
        List<Point> path = new ArrayList<>();
        Point iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.getParent();
        }

        return path;
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
