package com.weather.service.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SinglePageRequest {
    @Builder.Default
    private int pageNumber = 0;

    @Builder.Default
    private int pageSize = 10;

    @Builder.Default
    private Sort.Direction sortDirection = Sort.Direction.DESC;
}
