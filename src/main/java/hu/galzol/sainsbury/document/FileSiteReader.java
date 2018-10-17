package hu.galzol.sainsbury.document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

public class FileSiteReader implements SiteReader {

    @Override
    public Document getDocument(String fileUri) throws IOException {
        File f = new File(URI.create(fileUri));
        if (!f.exists()) throw new FileNotFoundException("Resource file not found: " + fileUri);
        return Jsoup.parse(f, "UTF-8", f.getParentFile().toURI().toURL().toExternalForm());
    }

}
