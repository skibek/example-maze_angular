package org.example.maze.service;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.example.maze.MazeApplication;
import org.example.maze.domain.MazeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(classes = MazeApplication.class)
public class RepoServiceTest {

    @Autowired
    private RepoService repoService;

    private final int nrOfElements = 10;
    private final int nrOfElementsOnPage = 3;

    @BeforeEach
    public void init() {
        repoService.initFakerData(nrOfElements);
    }

    @Test
    public void assertThatPageMechanismWorks() {
        //repoService.listMazeInfo.forEach(mazeInfo -> log.info(mazeInfo.toString()));

        Pageable firstPageWithTwoElements = PageRequest.of(1, nrOfElementsOnPage);
        Page<MazeInfo> page = repoService.getPageableMazeInfo(firstPageWithTwoElements);
        page.getTotalElements();

        assertThat(page.getTotalElements()).isEqualTo(nrOfElements);
        assertThat(page.getContent().size()).isEqualTo(nrOfElementsOnPage);
        assertThat((double)page.getTotalPages()).isEqualTo(Math.ceil((double) nrOfElements/(double)nrOfElementsOnPage));
    }
}
