package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {

    private FileService fileService;
    private FileController fileController;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    public void whenFileGetByIdThenGetFile() {
        var fileDto = new FileDto("test.img", new byte[]{1, 2, 3});
        when(fileService.getFileById(anyInt())).thenReturn(Optional.of(fileDto));
        var result = fileController.getById(anyInt());
        assertThat(result).isEqualTo(ResponseEntity.ok(fileDto.getContent()));
    }

    @Test
    public void whenFileGetByIdThenFileNotFound() {
        when(fileService.getFileById(anyInt())).thenReturn(Optional.empty());
        var result = fileController.getById(anyInt());
        assertThat(result).isEqualTo(ResponseEntity.notFound().build());
    }
}