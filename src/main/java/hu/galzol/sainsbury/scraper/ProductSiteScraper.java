package hu.galzol.sainsbury.scraper;

import hu.galzol.sainsbury.document.SiteReader;
import org.jsoup.nodes.Document;

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
        } catch (Exception e) {
            log.warning("Resource read error: " + source + "\nMessage: " + e.getMessage());
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
                .map(d -> new ProductDocumentReader(d).title().description().unitPrice().kcal().build());
    }

    public ProductSummary getAllProducts(String source) {
        List<Product> products = searchProductLinks(source).parallelStream()
                .map(this::getProductDetails)
                .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
                .collect(Collectors.toList());
        return ProductSummary.summarize(products);
    }
}
