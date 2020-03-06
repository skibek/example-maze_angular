package org.example.maze.service;

import com.github.javafaker.Faker;
import org.example.maze.domain.MazeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RepoService {

    public List<MazeInfo> listMazeInfo = new ArrayList<>();

    public void add(MazeInfo mazeInfo) {
        listMazeInfo.add(mazeInfo);
    }

    public Page<MazeInfo> getPageableMazeInfo(Pageable pageable) {
        int skipParam = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() * pageable.getPageSize();
        List<MazeInfo> listForPage = listMazeInfo
                .stream()
                .skip(skipParam)
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<MazeInfo>(listForPage,  pageable, listMazeInfo.size());
        /*
        pageable.first()
        pageable.getOffset() , pageable.getPageNumber(), pageable.getPageSize()
        */
    }

    public void initFakerData(int nrOfElements) {
        List<MazeInfo> listMazeInfo = IntStream.range(0, nrOfElements)
                .mapToObj(obj -> createMazeInfo_faker())
                .collect(Collectors.toList());
        listMazeInfo.forEach(this::add);
    }

    private MazeInfo createMazeInfo_faker() {
        Faker faker = new Faker();

        return MazeInfo.builder()
                .createDate(faker.date().birthday())
                .fileName(faker.file().fileName())
                .uuid(UUID.randomUUID())
                .build();
    }
}
