package com.bharath.theatreservice.dto.seat;


import com.bharath.theatreservice.entity.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatResponse {
    private Long id;
    private String seatNumber;
    private String rowNumber;
    private SeatType seatType;
    private Double price;
    private Long screenId;
}
