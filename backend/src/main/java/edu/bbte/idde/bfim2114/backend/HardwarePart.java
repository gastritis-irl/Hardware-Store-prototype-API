
package edu.bbte.idde.bfim2114.backend;

public class HardwarePart {
    private Long id;
    private String name;
    private String manufacturer;
    private String category;
    private Double price;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Manufacturer: %s, Category: %s, Price: %.2f, Description: %s",
                id, name, manufacturer, category, price, description);
    }
}
