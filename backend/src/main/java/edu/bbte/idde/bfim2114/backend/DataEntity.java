package edu.bbte.idde.bfim2114.backend;

import java.util.Objects;

public class DataEntity {
    private Long id;
    private String value;

    public DataEntity(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object item) {
        if (this == item) {
            return true;
        }
        if (item == null || getClass() != item.getClass()) {
            return false;
        }
        DataEntity that = (DataEntity) item;
        return Objects.equals(id, that.id) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }
}
