package com.bharath.theatreservice.service;

import com.bharath.theatreservice.dto.theatre.TheatreRequest;
import com.bharath.theatreservice.dto.theatre.TheatreResponse;

import java.util.List;

public interface TheatreService {
    TheatreResponse createTheatre(TheatreRequest request);
    TheatreResponse updateTheatre(TheatreRequest request, Long theatreId);
    String deleteTheatre(Long theatreId);
    List<TheatreResponse> getTheatreByName(String name);
    List<TheatreResponse> getAllTheatres();
}
