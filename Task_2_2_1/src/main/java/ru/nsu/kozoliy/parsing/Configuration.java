package ru.nsu.kozoliy.parsing;

import java.util.List;
import ru.nsu.kozoliy.dto.BackerDto;
import ru.nsu.kozoliy.dto.CourierDto;
import ru.nsu.kozoliy.dto.StorageDto;

/**
 * Конфигурация пиццерии, содержащая информацию о курьерах, пекарях и складе.
 */
public record Configuration(List<CourierDto> couriers,
                            List<BackerDto> backers,
                            StorageDto storage) {
}
