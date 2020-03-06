package org.example.maze.service;

import lombok.RequiredArgsConstructor;
import org.example.maze.domain.MazeInfo;
import org.example.maze.model.Maze;
import org.example.maze.model.MazeSolverEnum;
import org.example.maze.service.tools.MazeSolverDFSTools;
import org.example.maze.service.tools.MazeSolverBFSTools;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MazeSolverService {

    private final MazeSolverBFSTools mazeSolverBFSTools;
    private final MazeSolverDFSTools mazeSolverDFSTools;

    public MazeInfo solveMaze(MazeSolverEnum method, Maze maze) {
        switch (method) {
            case Depth_First_Search:
                return mazeSolverDFSTools.solve_DepthFirst(maze);
            case Breadth_First_Search:
                return mazeSolverBFSTools.solve_BreadthFirst(maze);
            default:
                return null;
        }
    }

}
