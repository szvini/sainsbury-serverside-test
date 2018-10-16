package hu.galzol.sainsbury.scraper;

import hu.galzol.sainsbury.document.ResourceFileSiteReader;
import org.junit.Test;

import java.util.List;

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

}
