package hu.galzol.sainsbury.scraper;

import hu.galzol.sainsbury.document.ResourceFileSiteReader;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SiteScraperTest {

    private SiteScraper siteScraper = new SiteScraper(new ResourceFileSiteReader());

    @Test
    public void testCollectProductLinks() {
        List<String> productLinks = siteScraper.searchProductLinks("/sainsbury/product-list.html");
        assertThat(productLinks).hasSize(17);
    }

    @Test
    public void testCollectProductLinksInInvalidSite() {
        assertThat(siteScraper.searchProductLinks("nonexisting")).hasSize(0);
    }

    @Test
    public void testCollectProductLinksSkipCrossSellItems() {
        List<String> links = siteScraper.searchProductLinks("/sainsbury/product-list.html");
        assertThat(links).doesNotContain("sainsburys-140ml-klip-lock-storage-set---3pk.html");
    }

    @Test
    public void testProductInfoLookup() {
        Optional<Product> result = siteScraper.getProductDetails("/sainsbury/sainsburys-british-strawberries-400g.html");
        assertThat(result).isPresent();
        Product p = result.get();
        assertThat(p.getTitle()).isEqualTo("Sainsbury's Strawberries 400g");
        assertThat(p.getDescription()).isEqualTo("by Sainsbury's strawberries");
        assertThat(p.getKcalPer100g()).hasValue(33);
        assertThat(p.getUnitPrice()).isEqualTo(new BigDecimal("1.75"));
    }

}
