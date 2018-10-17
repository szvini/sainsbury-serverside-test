package hu.galzol.sainsbury.scraper;

import hu.galzol.sainsbury.document.SiteReader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductSiteScraper {

    private static final Logger log = Logger.getLogger(ProductSiteScraper.class.getClass().getName());

    private SiteReader reader;

    public ProductSiteScraper(SiteReader reader) {
        this.reader = reader;
    }

    public Optional<Document> openSite(String source) {
        try {
            return Optional.of(reader.getDocument(source));
        } catch (IOException e) {
            log.warning("Resource IO error: " + source + "\nMessage: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<String> searchProductLinks(String source) {
        return openSite(source)
                .map(d -> d.body().select(".product .productNameAndPromotions a"))
                .map(e -> e.eachAttr("abs:href"))
                .orElse(Collections.emptyList());
    }

    public Optional<Product> getProductDetails(String productSource) {
        return openSite(productSource)
                .map(d -> {

                    String title = Optional.ofNullable(d.body().selectFirst(".productSummary .productTitleDescriptionContainer h1"))
                            .map(Element::text)
                            .orElse("");

                    String description = Optional.ofNullable(d.body().selectFirst(".mainProductInfo .productDataItemHeader + .productText p:first-child"))
                            .map(e -> e.text().trim())
                            .orElse("");

                    Optional<Integer> kcal = Optional.ofNullable(d.body().selectFirst(".mainProductInfo .productText .nutritionTable td:contains(kcal)"))
                            .map(e -> Integer.valueOf(e.text().replaceFirst("kcal", "").trim()));

                    BigDecimal unitPrice = Optional.ofNullable(d.body().selectFirst(".productSummary .pricePerUnit"))
                            .map(e -> new BigDecimal(e.text().replaceFirst("£([\\d.]+)\\/unit", "$1")))
                            .orElse(BigDecimal.ZERO);

                    Product p = new Product();
                    p.setTitle(title);
                    p.setDescription(description);
                    p.setKcalPer100g(kcal);
                    p.setUnitPrice(unitPrice);

                    return p;
                });
    }

    public ProductSummary getAllProducts(String source) {
        List<Product> products = searchProductLinks(source).parallelStream()
                .map(this::getProductDetails)
                .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
                .collect(Collectors.toList());
        return ProductSummary.summarize(products);
    }
}
