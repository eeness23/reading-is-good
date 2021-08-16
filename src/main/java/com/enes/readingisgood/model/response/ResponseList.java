package com.enes.readingisgood.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseList<T> {
    private List<T> items = List.of();
    private Integer page;
    private Integer size;
    private Long totalSize;

    public ResponseList(List<T> items) {
        this.items = items;
    }

    public Integer getSize() {
        return size != null ? size : items.size();
    }
}
