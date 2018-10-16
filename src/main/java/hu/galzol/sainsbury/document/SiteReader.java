package hu.galzol.sainsbury.document;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface SiteReader {
    Document getDocument(String source) throws IOException;
}
