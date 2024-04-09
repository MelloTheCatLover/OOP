package ru.nsu.kozoliy.parsing;

import ru.nsu.kozoliy.dto.BackerDto;
import ru.nsu.kozoliy.dto.CourierDto;
import ru.nsu.kozoliy.dto.StorageDto;

import java.util.List;

/**
 * Конфигурация пиццерии, содержащая информацию о курьерах, пекарях и складе.
 */
public record Configuration(List<CourierDto> couriers, List<BackerDto> backers, StorageDto storage) {
}
