package ru.nsu.fit.directors.orderservice.dto.response;

public record EstablishmentDto(
    String image,
    String name,
    String cuisineCountry,
    String rating,

    Integer starsCount,
    String category
) {
}
