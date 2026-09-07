package com.bharath.theatreservice.service.impl;

import com.bharath.theatreservice.dto.seat.SeatRequest;
import com.bharath.theatreservice.dto.seat.SeatResponse;
import com.bharath.theatreservice.entity.Screen;
import com.bharath.theatreservice.entity.Seat;
import com.bharath.theatreservice.exception.ResourceNotFoundException;
import com.bharath.theatreservice.repository.ScreenRepository;
import com.bharath.theatreservice.repository.SeatRepository;
import com.bharath.theatreservice.service.SeatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    @Override
    @Transactional
    public SeatResponse createSeat(SeatRequest request) {
        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        if(seatRepository.existsBySeatNumberAndScreen_Id(request.getSeatNumber(), request.getScreenId()))
            throw new RuntimeException("Seat already exists in theatre");

        Seat seat = Seat.builder()
                .seatNumber(request.getSeatNumber())
                .rowNumber(request.getRowNumber())
                .seatType(request.getSeatType())
                .price(request.getPrice())
                .screen(screen)
                .build();
        Seat savedSeat = seatRepository.save(seat);
        return mapToSeatResponse(savedSeat);
    }

    @Override
    public List<SeatResponse> getSeatsByScreen(Long screenId) {
        if(!screenRepository.existsById(screenId))
            throw new ResourceNotFoundException("Screen not found");
        return seatRepository.findByScreen_Id(screenId).stream().map(this::mapToSeatResponse).toList();
    }

    @Override
    public List<SeatResponse> getAllSeats() {
        return seatRepository.findAll().stream().map(this::mapToSeatResponse).toList();
    }

    @Override
    public SeatResponse getSeatById(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        return mapToSeatResponse(seat);
    }

    @Override
    @Transactional
    public SeatResponse updateSeat(Long seatId, SeatRequest request) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        if(seatRepository.existsBySeatNumberAndScreen_IdAndIdNot(request.getSeatNumber(), request.getScreenId(), seatId))
            throw new RuntimeException("Seat already exists in theatre");

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        seat.setSeatNumber(request.getSeatNumber());
        seat.setRowNumber(request.getRowNumber());
        seat.setSeatType(request.getSeatType());
        seat.setPrice(request.getPrice());
        seat.setScreen(screen);

        Seat updatedSeat = seatRepository.save(seat);
        return mapToSeatResponse(updatedSeat);
    }

    @Override
    public String deleteSeat(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        seatRepository.delete(seat);
        return "Seat has been deleted";
    }

    private SeatResponse mapToSeatResponse(Seat seat) {
        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .rowNumber(seat.getRowNumber())
                .seatType(seat.getSeatType())
                .price(seat.getPrice())
                .screenId(seat.getScreen().getId())
                .build();
    }
}
