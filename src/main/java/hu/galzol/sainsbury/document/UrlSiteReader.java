package hu.galzol.sainsbury.document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UrlSiteReader implements SiteReader {

    @Override
    public Document getDocument(String source) throws Exception {
        return Jsoup.connect(source).get();
    }

}
