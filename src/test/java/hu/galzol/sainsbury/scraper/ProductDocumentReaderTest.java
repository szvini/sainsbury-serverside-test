package hu.galzol.sainsbury.scraper;

import hu.galzol.sainsbury.document.FileSiteReader;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDocumentReaderTest {

    private ProductDocumentReader productReader = testProductReader();

    @Test
    public void testTitleLookup() {
        assertThat(productReader.title().build().getTitle()).isEqualTo("Sainsbury's Strawberries 400g");
    }

    @Test
    public void testDescLookup() {
        assertThat(productReader.description().build().getDescription()).isEqualTo("by Sainsbury's strawberries");
    }

    @Test
    public void testKcalLookup() {
        assertThat(productReader.kcal().build().getKcalPer100g()).isEqualTo(33);
    }

    @Test
    public void testUnitPriceLookup() {
        assertThat(productReader.unitPrice().build().getUnitPrice()).isEqualByComparingTo(new BigDecimal("1.75"));
    }

    private ProductDocumentReader testProductReader() {
        try {
            String testResource = "/sainsbury/sainsburys-british-strawberries-400g.html";
            Document doc = new FileSiteReader().getDocument(this.getClass().getResource(testResource).toExternalForm());
            return new ProductDocumentReader(doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
