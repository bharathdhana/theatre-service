package com.bharath.theatreservice.dto.response;

import com.bharath.theatreservice.entity.enums.TheatreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheatreResponse {
    private Long id;
    private String name;
    private String location;
    private String city;
    private TheatreStatus status;
}
