package com.paazl.model;

import java.util.List;

public class SheepOrderDto {
    private List<SheepDto> orderedSheeps;

    public List<SheepDto> getOrderedSheeps() {
        return orderedSheeps;
    }

    public void setOrderedSheeps(List<SheepDto> orderedSheeps) {
        this.orderedSheeps = orderedSheeps;
    }
}
