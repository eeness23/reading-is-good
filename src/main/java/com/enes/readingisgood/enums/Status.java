package com.enes.readingisgood.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {

    ACTIVE(1),
    PASSIVE(0),
    DELETED(-1);

    private final Integer value;
}
