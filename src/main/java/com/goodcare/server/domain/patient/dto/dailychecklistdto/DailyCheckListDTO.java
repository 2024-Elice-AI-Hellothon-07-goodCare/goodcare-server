package com.goodcare.server.domain.patient.dto.dailychecklistdto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyCheckListDTO {
    private LocalDate createdAt;
}
