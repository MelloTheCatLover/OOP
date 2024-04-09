package ru.nsu.kozoliy.Parsing;

import ru.nsu.kozoliy.Dto.BackerDto;
import ru.nsu.kozoliy.Dto.CourierDto;
import ru.nsu.kozoliy.Dto.StorageDto;

import java.util.List;

public record Configuration(List<CourierDto> couriers, List<BackerDto> backers, StorageDto storage) {
}