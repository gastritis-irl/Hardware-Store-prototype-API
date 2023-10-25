package edu.bbte.idde.bfim2114.backend.model;

import java.util.Objects;

public class DataEntity extends BaseEntity {

    private String value;

    public DataEntity(Long id, String value) {
        super();
        this.id = id;
        this.value = value;
    }

    public DataEntity(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        DataEntity that = (DataEntity) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
