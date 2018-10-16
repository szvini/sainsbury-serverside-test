package hu.galzol.sainsbury.scraper;

import java.math.BigDecimal;
import java.util.Optional;

public class Product {

    private String title;
    private String description;
    private BigDecimal unitPrice;
    private Optional<Integer> kcalPer100g;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Optional<Integer> getKcalPer100g() {
        return kcalPer100g;
    }

    public void setKcalPer100g(Optional<Integer> kcalPer100g) {
        this.kcalPer100g = kcalPer100g;
    }
}
