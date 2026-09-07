package com.bharath.theatreservice.service.impl;


import com.bharath.theatreservice.dto.screen.ScreenRequest;
import com.bharath.theatreservice.dto.screen.ScreenResponse;
import com.bharath.theatreservice.entity.Screen;
import com.bharath.theatreservice.entity.Theatre;
import com.bharath.theatreservice.exception.ResourceNotFoundException;
import com.bharath.theatreservice.repository.ScreenRepository;
import com.bharath.theatreservice.repository.TheatreRepository;
import com.bharath.theatreservice.service.ScreenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    @Override
    @Transactional
    public ScreenResponse createScreen(ScreenRequest request) {
        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() -> new ResourceNotFoundException("theatre not found"));

        if(screenRepository.existsByScreenNumberAndTheatre_Id(request.getScreenNumber(), request.getTheatreId()))
            throw new RuntimeException("Screen already exists in this theatre");

        Screen screen = Screen.builder()
                .screenNumber(request.getScreenNumber())
                .capacity(request.getCapacity())
                .theatre(theatre)
                .build();
        Screen savedScreen = screenRepository.save(screen);
        return mapToScreenResponse(savedScreen);
    }

    @Override
    public ScreenResponse getScreenById(Long id) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));
        return mapToScreenResponse(screen);
    }

    @Override
    public List<ScreenResponse> getScreensByTheater(Long theatreId) {
        return screenRepository.findByTheatre_Id(theatreId).stream().map(this::mapToScreenResponse).toList();
    }

    @Override
    @Transactional
    public ScreenResponse updateScreen(Long id, ScreenRequest request) {
        Screen screen =  screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() -> new ResourceNotFoundException("theatre not found"));

        boolean duplicate = screenRepository.existsByScreenNumberAndTheatre_IdAndIdNot(request.getScreenNumber(), theatre.getId(), id);
        if(duplicate) {
            throw new RuntimeException("Screen already exists in this theatre");
        }

        screen.setScreenNumber(request.getScreenNumber());
        screen.setCapacity(request.getCapacity());
        screen.setTheatre(theatre);
        Screen updated = screenRepository.save(screen);
        return mapToScreenResponse(updated);
    }

    @Override
    @Transactional
    public String deleteScreen(Long id) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        screenRepository.delete(screen);
        return "Screen has been deleted";
    }

    private ScreenResponse mapToScreenResponse(Screen screen) {
        return ScreenResponse.builder()
                .id(screen.getId())
                .screenNumber(screen.getScreenNumber())
                .capacity(screen.getCapacity())
                .theatreId(screen.getTheatre().getId())
                .build();
    }
}
