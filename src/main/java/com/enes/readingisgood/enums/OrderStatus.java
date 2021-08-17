package com.enes.readingisgood.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PURCHASED(1),
    CANCELLED(0);

    private final Integer value;

    public static OrderStatus of(Integer value) {
        return Stream.of(OrderStatus.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow();
    }
}
