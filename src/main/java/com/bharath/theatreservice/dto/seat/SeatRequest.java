package com.bharath.theatreservice.dto.seat;

import com.bharath.theatreservice.entity.enums.SeatType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatRequest {
    @NotBlank(message = "seat Number is required")
    @Pattern( regexp = "^[A-Z][0-9]+$", message = "Seat number must be in format A1, A2, B1, B2" )
    private String seatNumber;

    @NotNull(message = "row Number is required")
    @Pattern( regexp = "^[A-Z]$", message = "Row number must be a single uppercase letter" )
    private String rowNumber;

    @NotNull(message = "seat type is required")
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @NotNull(message = "price is required")
    private Double price;

    @NotNull(message = "screen ID is required")
    private Long screenId;
}