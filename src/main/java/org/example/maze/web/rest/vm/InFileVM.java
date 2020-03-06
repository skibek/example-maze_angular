package org.example.maze.web.rest.vm;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@ToString
public class InFileVM {

    @NotEmpty
    private String fileName;
}
