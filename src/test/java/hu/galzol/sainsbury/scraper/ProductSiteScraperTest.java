package hu.galzol.sainsbury.scraper;

import hu.galzol.sainsbury.document.FileSiteReader;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductSiteScraperTest {

    private ProductSiteScraper siteScraper = new ProductSiteScraper(new FileSiteReader());

    @Test
    public void testCollectProductLinks() {
        List<String> productLinks = siteScraper.searchProductLinks(getResourceURL("/sainsbury/product-list.html"));
        assertThat(productLinks).hasSize(17);
    }

    @Test
    public void testCollectProductLinksInInvalidSite() {
        assertThat(siteScraper.searchProductLinks("nonexisting")).hasSize(0);
    }

    @Test
    public void testCollectProductLinksSkipCrossSellItems() {
        List<String> links = siteScraper.searchProductLinks(getResourceURL("/sainsbury/product-list.html"));
        assertThat(links).doesNotContain("sainsburys-140ml-klip-lock-storage-set---3pk.html");
    }

    @Test
    public void testProductInfoLookup() {
        Optional<Product> result = siteScraper.getProductDetails(getResourceURL("/sainsbury/sainsburys-british-strawberries-400g.html"));
        assertThat(result).isPresent();
        Product p = result.get();
        assertThat(p.getTitle()).isEqualTo("Sainsbury's Strawberries 400g");
        assertThat(p.getDescription()).isEqualTo("by Sainsbury's strawberries");
        assertThat(p.getKcalPer100g()).isEqualTo(33);
        assertThat(p.getUnitPrice()).isEqualTo(new BigDecimal("1.75"));
    }

    @Test
    public void testAllProductLookup() {
        ProductSummary summary = siteScraper.getAllProducts(getResourceURL("/sainsbury/product-list.html"));

        assertThat(summary.getResults()).hasSize(2);
        assertThat(summary.getTotal().getGross()).isEqualByComparingTo(new BigDecimal("3.25"));
        assertThat(summary.getTotal().getVat()).isEqualByComparingTo(new BigDecimal("0.65"));

        assertThat(summary.getResults().get(0).getTitle()).isEqualTo("Sainsbury's Strawberries 400g");
        assertThat(summary.getResults().get(0).getDescription()).isEqualTo("by Sainsbury's strawberries");
        assertThat(summary.getResults().get(0).getKcalPer100g()).isEqualTo(33);
        assertThat(summary.getResults().get(0).getUnitPrice()).isEqualByComparingTo(new BigDecimal("1.75"));

        assertThat(summary.getResults().get(1).getTitle()).isEqualTo("Sainsbury's Blackberries, Sweet 150g");
        assertThat(summary.getResults().get(1).getDescription()).isEqualTo("by Sainsbury's blackberries");
        assertThat(summary.getResults().get(1).getKcalPer100g()).isEqualTo(32);
        assertThat(summary.getResults().get(1).getUnitPrice()).isEqualByComparingTo(new BigDecimal("1.5"));
    }

    public String getResourceURL(String source) {
        URL url = this.getClass().getResource(source);
        return (url == null) ? null : url.toExternalForm();
    }
}
