package hu.galzol.sainsbury.scraper;

import hu.galzol.sainsbury.document.SiteReader;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SiteScraper {

    private SiteReader reader;

    public SiteScraper(SiteReader reader) {
        this.reader = reader;
    }

    public Optional<Document> openSite(String source) {
        try {
            return Optional.of(reader.getDocument(source));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<String> searchProductLinks(String source) {
        return openSite(source)
                .map(d -> d.body().select(".product .productNameAndPromotions a"))
                .map(e -> e.eachAttr("abs:href"))
                .orElse(Collections.emptyList());
    }

}
