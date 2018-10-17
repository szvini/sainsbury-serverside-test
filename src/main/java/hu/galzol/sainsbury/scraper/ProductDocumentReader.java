package hu.galzol.sainsbury.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Logger;

public class ProductDocumentReader {

    private static final Logger log = Logger.getLogger(ProductSiteScraper.class.getClass().getName());

    private final Document doc;
    private Product product = new Product();

    public ProductDocumentReader(Document doc) {
        this.doc = doc;
    }

    public ProductDocumentReader title() {
        product.setTitle(Optional.ofNullable(doc.body().selectFirst(".productSummary .productTitleDescriptionContainer h1"))
                .map(Element::text)
                .orElse("")
        );
        return this;
    }

    public ProductDocumentReader description() {
        product.setDescription(Optional.ofNullable(doc.body().selectFirst(".mainProductInfo .productDataItemHeader + .productText p:first-child"))
                .map(e -> e.text().trim())
                .orElse("")
        );
        return this;
    }

    public ProductDocumentReader kcal() {
        product.setKcalPer100g(Optional.ofNullable(doc.body().selectFirst(".mainProductInfo .productText .nutritionTable td:contains(kcal)"))
                .flatMap(e -> {
                    try {
                        return Optional.of(Integer.valueOf(e.text().replaceFirst("kcal", "").trim()));
                    } catch (NumberFormatException ex) {
                        log.warning("Invalid kcal: " + e.text());
                        return Optional.empty();
                    }
                })
        );
        return this;
    }

    public ProductDocumentReader unitPrice() {
        product.setUnitPrice(Optional.ofNullable(doc.body().selectFirst(".productSummary .pricePerUnit"))
                .flatMap(e -> {
                    try {
                        return Optional.of(new BigDecimal(e.text().replaceFirst("£([\\d.]+)\\/unit", "$1")));
                    } catch (NumberFormatException ex) {
                        log.warning("Invalid unit price: " + e.text());
                        return Optional.empty();
                    }
                })
                .orElse(BigDecimal.ZERO)
        );

        return this;
    }

    public Product build() {
        Product p = new Product();
        p.setTitle(product.getTitle());
        p.setDescription(product.getDescription());
        p.setKcalPer100g(Optional.ofNullable(product.getKcalPer100g()));
        p.setUnitPrice(product.getUnitPrice());
        return p;
    }


}
