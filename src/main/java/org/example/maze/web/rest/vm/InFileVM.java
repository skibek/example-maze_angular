package org.example.maze.web.rest.vm;

import lombok.*;
import org.example.maze.model.MazeSolutionMode;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class InFileVM {

    @NotEmpty
    private String fileName;

    private MazeSolutionMode mode;

}
