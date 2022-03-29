package ru.liga.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.liga.domain.SexType;

@Getter
@RequiredArgsConstructor
public class ApplicationDTO {

    private final String name;
    private final String desc;
    private final SexType sexType;
    private final SexType lookingFor;
}
