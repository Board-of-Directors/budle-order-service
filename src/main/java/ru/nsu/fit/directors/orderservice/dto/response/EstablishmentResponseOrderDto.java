package ru.nsu.fit.directors.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@Setter
@Accessors(chain = true)
public class EstablishmentResponseOrderDto {
    private Long id;
    private Integer guestCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time endTime;
    private Integer status;
    private Long establishmentId;
    private String guestName;
    private List<ActionDto> businessActions;
}
