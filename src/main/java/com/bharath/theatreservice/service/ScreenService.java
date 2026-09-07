package com.bharath.theatreservice.service;

import com.bharath.theatreservice.dto.screen.ScreenRequest;
import com.bharath.theatreservice.dto.screen.ScreenResponse;

import java.util.List;

public interface ScreenService {
    ScreenResponse createScreen(ScreenRequest request);
    ScreenResponse getScreenById(Long id);
    List<ScreenResponse> getScreensByTheater(Long theaterId);
    ScreenResponse updateScreen(Long id, ScreenRequest request);
    String deleteScreen(Long id);
}
