package com.bharath.theatreservice.entity;


import com.bharath.theatreservice.entity.enums.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern( regexp = "^[A-Z][0-9]+$", message = "Seat number must be in format A1, A2, B1, B2" )
    private String seatNumber;

    @Column(nullable = false)
    @Pattern( regexp = "^[A-Z]$", message = "Row number must be a single uppercase letter" )
    private String rowNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

}
