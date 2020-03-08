package org.example.maze.service;

import lombok.RequiredArgsConstructor;
import org.example.maze.domain.MazeInfo;
import org.example.maze.domain.MazeInfoPath;
import org.example.maze.model.Maze;
import org.example.maze.model.MazeSolutionMode;
import org.example.maze.model.MazeSolverEnum;
import org.example.maze.service.tools.MazeSolverDFSTools;
import org.example.maze.service.tools.MazeSolverBFSTools;
import org.example.maze.service.tools.MazeTools;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MazeSolverService {

    private final MazeTools mazeTools;
    private final RepoService repoService;
    private final MazeSolverBFSTools mazeSolverBFSTools;
    private final MazeSolverDFSTools mazeSolverDFSTools;

    public MazeInfo solveMazeAndSave(MazeSolverEnum method, String mazeBody, MazeSolutionMode mode) {
        MazeInfo mazeInfo = solveMaze(method, mazeBody);
        displaySuccessfulPath(mazeInfo);
        repoService.add(mazeInfo);

        if (mode == MazeSolutionMode.LEAST_CHANGES_OF_DIRECTION) {
            getLeastChangesOfDirection(mazeInfo);
        }

        return mazeInfo;
    }

    public MazeInfo solveMaze(MazeSolverEnum method, String mazeBody) {
        Maze maze = mazeTools.initializeMaze(mazeBody);

        switch (method) {
            case Depth_First_Search:
                return mazeSolverDFSTools.solve_DepthFirst(maze);
            case Breadth_First_Search:
                return mazeSolverBFSTools.solve_BreadthFirst(maze);
            default:
                return null;
        }
    }

    private void displaySuccessfulPath(MazeInfo mazeInfo) {
        for (MazeInfoPath mazeInfoPath : mazeInfo.getMazeInfoPaths()) {
            mazeTools.placeRouteOnMaze(mazeInfo.getMaze(), mazeInfoPath.getSuccesfulPathList(), false);
            mazeInfoPath.setMazeWithResult(mazeInfo.getMaze().displayGraphics());
        }
    }

    private void getLeastChangesOfDirection(MazeInfo mazeInfo) {
        MazeInfoPath minByCounter = mazeInfo.getMazeInfoPaths()
                .stream()
                .min(Comparator.comparing(MazeInfoPath::getCounterChangeDirection))
                .orElseThrow(NoSuchElementException::new);
        mazeInfo.getMazeInfoPaths().clear();
        mazeInfo.getMazeInfoPaths().add(minByCounter);
    }

}
