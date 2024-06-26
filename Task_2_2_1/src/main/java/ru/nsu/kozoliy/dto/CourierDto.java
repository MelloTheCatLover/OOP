package ru.nsu.kozoliy.dto;

/**
 * Класс, представляющий DTO (Data Transfer Object) для курьера.
 *
 */
public record CourierDto(int id, String name, String surname, int baggageSize, int deliveryTime) {
}
