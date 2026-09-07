package com.bharath.theatreservice.dto.screen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenResponse {
    private Long id;
    private Integer screenNumber;
    private Integer capacity;
    private Long theatreId;
}
