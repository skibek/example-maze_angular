package org.example.maze.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.maze.domain.MazeInfo;
import org.example.maze.service.FileService;
import org.example.maze.service.RepoService;
import org.example.maze.service.tools.VersionInfo;
import org.example.maze.web.rest.vm.InFileVM;
import org.example.maze.web.rest.vm.OutMazeFileVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(MazeResource.ENTITIES_URI)
public class MazeResource {

    public static final String ENTITIES_URI = VersionInfo.apiVersionPath + "maze";

    private final FileService fileService;
    private final RepoService repoService;

    @PostMapping
    public ResponseEntity<OutMazeFileVM> uploadMazeFile(@RequestParam(value = "inFileVM") String inFileVM,
                                                        @RequestPart(value = "file") MultipartFile multipartFile)
            throws URISyntaxException, IOException {
        return processFile(inFileVM, multipartFile);
    }

    @GetMapping
    public ResponseEntity<Page<MazeInfo>> getMazeList(Pageable pageable) {
        log.info("getMazeList pageable=" + pageable);
        final Page<MazeInfo> page = repoService.getPageableMazeInfo(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/init")
    public ResponseEntity initRepo() {
        log.info("Init faker data");
        repoService.initFakerData(10);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<OutMazeFileVM> processFile(String metadata, MultipartFile multipartFile)
            throws URISyntaxException, IOException {
        InFileVM inFileVM = new ObjectMapper().readValue(metadata, InFileVM.class);
        log.info("upload MazeFile = " + inFileVM);
        OutMazeFileVM vm = fileService.consumeMazeFile(inFileVM, multipartFile);
        return ResponseEntity.created(new URI(ENTITIES_URI + "/" +  vm.getUuid())).body(vm);
    }
}
