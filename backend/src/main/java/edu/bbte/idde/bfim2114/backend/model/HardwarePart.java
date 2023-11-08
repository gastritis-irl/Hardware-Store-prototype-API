package edu.bbte.idde.bfim2114.backend.model;

public class HardwarePart extends BaseEntity {

    private String name;
    private String manufacturer;
    private String category;
    private Double price;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
                manufacturer != null && !manufacturer.trim().isEmpty() &&
                category != null && !category.trim().isEmpty() &&
                price != null && price > 0;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Manufacturer: %s, Category: %s, Price: %.2f, Description: %s",
                id, name, manufacturer, category, price, description);
    }
}
