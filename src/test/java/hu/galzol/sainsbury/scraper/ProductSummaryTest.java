package hu.galzol.sainsbury.scraper;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductSummaryTest {

    @Test
    public void testProductsSummary() {
        Product p1 = new Product();
        p1.setUnitPrice(new BigDecimal(1.2));

        Product p2 = new Product();
        p2.setUnitPrice(new BigDecimal(1.8));

        ProductSummary summary = ProductSummary.summarize(Arrays.asList(p1, p2));

        assertThat(summary.getResults()).hasSize(2);
        assertThat(summary.getResults()).containsOnly(p1, p2);
        assertThat(summary.getTotal().getGross()).isEqualByComparingTo(new BigDecimal("3"));
        assertThat(summary.getTotal().getVat()).isEqualByComparingTo(new BigDecimal("0.6"));
    }


    @Test
    public void testRounding() {
        Product p1 = new Product();
        p1.setUnitPrice(new BigDecimal(0.335));

        ProductSummary summary = ProductSummary.summarize(Collections.singletonList(p1));

        assertThat(summary.getTotal().getGross()).isEqualByComparingTo(new BigDecimal("0.34"));
        assertThat(summary.getTotal().getVat()).isEqualByComparingTo(new BigDecimal("0.07"));
    }

}
