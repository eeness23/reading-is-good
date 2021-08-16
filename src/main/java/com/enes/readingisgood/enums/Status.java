package com.enes.readingisgood.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Status {

    ACTIVE(1),
    PASSIVE(0),
    DELETED(-1);

    private final Integer value;

    public static Status of(Integer value) {
        return Stream.of(Status.values())
                .filter(status -> status.getValue().equals(value))
                .findFirst()
                .orElseThrow();
    }
}
