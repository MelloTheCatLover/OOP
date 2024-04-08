package ru.nsu.kozoliy.Parsing;

import ru.nsu.kozoliy.Backer.BackerDto;
import ru.nsu.kozoliy.Courier.CourierDto;
import ru.nsu.kozoliy.Storage.StorageDto;

import java.util.List;

public record Configuration(List<CourierDto> couriers, List<BackerDto> backers, StorageDto storage) {
}