package kr.pe.advenoh.quote.controller;

import kr.pe.advenoh.quote.model.dto.FolderResponseDto;
import kr.pe.advenoh.quote.model.dto.FolderStatsResponseDto;
import kr.pe.advenoh.quote.service.FolderService;
import kr.pe.advenoh.quote.spring.security.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/folders")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @GetMapping
    public ResponseEntity<?> getFolders(@CurrentUser Principal currentUser) {
        Map<String, Object> result = new HashMap<>();
        List<FolderResponseDto> folders = folderService.getFolders(currentUser.getName());
        FolderStatsResponseDto folderStatsResponseDto = FolderStatsResponseDto.builder()
                .totalNumOfQuotes(folders.stream().mapToLong(FolderResponseDto::getNumOfQuotes).sum())
                .totalNumOfLikes(0L)
                .build();
        result.put("folderStatInfo", folderStatsResponseDto);
        result.put("folderList", folders);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?>  createFolder(
            @RequestParam(value = "folderName") String folderName,
            @CurrentUser Principal currentUser) {
        return new ResponseEntity<>(folderService.createFolder(folderName, currentUser.getName()), HttpStatus.OK);
    }

    @PutMapping("/{folderId}/rename")
    public ResponseEntity<?>  renameFolder(
            @PathVariable(name = "folderId") Long folderId,
            @RequestParam(value = "folderName") String folderName) {
        Map<String, Object> result = new HashMap<>();
        folderService.renameFolder(folderId, folderName);
        result.put("succeed", true);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?>  deleteFolders(@RequestParam(value = "folderIds") List<Long> folderIds) {
        Map<String, Object> result = new HashMap<>();
        result.put("succeed", folderIds.size() == folderService.deleteFolders(folderIds));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
