package ru.nsu.kozoliy.dto;


/**
 * Класс, представляющий DTO (Data Transfer Object) для пекаря.
 *
 */
public record BackerDto(int id, String name, String surname, int workingTimeMs) {
}
