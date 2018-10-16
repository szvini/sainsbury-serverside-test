package hu.galzol.sainsbury.scraper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public class ProductSummary {
    private BigDecimal gross;
    private BigDecimal vat;
    private List<Product> products;

    private ProductSummary(List<Product> products) {
        this.products = Collections.unmodifiableList(products);
    }

    public static ProductSummary summarize(List<Product> products) {
        BigDecimal gross = products.stream().map(Product::getUnitPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal vat = gross.multiply(new BigDecimal("0.2")).setScale(2, RoundingMode.HALF_UP);
        ProductSummary s = new ProductSummary(products);
        s.gross = gross;
        s.vat = vat;
        return s;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public List<Product> getProducts() {
        return products;
    }

}
