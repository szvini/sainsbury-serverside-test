package hu.galzol.sainsbury.document;

import org.jsoup.nodes.Document;

public interface SiteReader {
    Document getDocument(String source) throws Exception;
}
