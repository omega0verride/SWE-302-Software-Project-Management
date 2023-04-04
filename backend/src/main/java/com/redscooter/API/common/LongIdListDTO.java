package com.redscooter.API.common;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LongIdListDTO {
    @NotNull(message = "Missing field: ids")
    private List<Long> ids;
}
