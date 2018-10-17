package hu.galzol.sainsbury.scraper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public class ProductSummary {

    private final ProductTotal total;
    private final List<Product> results;

    private ProductSummary(ProductTotal total, List<Product> results) {
        this.total = total;
        this.results = Collections.unmodifiableList(results);
    }

    public static ProductSummary summarize(List<Product> products) {
        BigDecimal gross = products.stream().map(Product::getUnitPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        BigDecimal vat = gross.multiply(new BigDecimal("0.2")).setScale(2, RoundingMode.HALF_UP);
        ProductTotal total = new ProductTotal(gross, vat);
        return new ProductSummary(total, products);
    }

    public ProductTotal getTotal() {
        return total;
    }

    public List<Product> getResults() {
        return results;
    }

}
