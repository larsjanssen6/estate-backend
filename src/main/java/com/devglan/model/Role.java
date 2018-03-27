package com.devglan.model;

public enum Role {
    PotentialMember(0), Member(1), Admin(2);

    private int index;

    Role(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
