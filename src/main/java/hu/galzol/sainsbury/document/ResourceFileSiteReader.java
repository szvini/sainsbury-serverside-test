package hu.galzol.sainsbury.document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class ResourceFileSiteReader implements SiteReader {

    @Override
    public Document getDocument(String source) throws IOException {
        URL url = this.getClass().getResource(source);
        if (url == null) throw new FileNotFoundException("Resource file not found: " + source);
        return Jsoup.parse(new File(url.getPath()), "UTF-8");
    }

}
