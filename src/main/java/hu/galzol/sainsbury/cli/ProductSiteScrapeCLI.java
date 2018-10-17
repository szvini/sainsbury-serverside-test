package hu.galzol.sainsbury.cli;

import hu.galzol.sainsbury.document.UrlSiteReader;
import hu.galzol.sainsbury.scraper.ProductSiteScraper;
import hu.galzol.sainsbury.scraper.ProductSummary;
import org.json.JSONObject;

public class ProductSiteScrapeCLI {

    public static void main(String ... args) {
        check(args);
        ProductSiteScraper scraper = new ProductSiteScraper(new UrlSiteReader());
        ProductSummary productSummary = scraper.getAllProducts(args[0]);
        JSONObject jsonObject = new JSONObject(productSummary);
        System.out.println(jsonObject.toString(4));
    }

    private static void check(String ... args) {
        if (args.length < 1) throw new IllegalArgumentException("Required site URL argument.");
        if (args.length > 1) throw new IllegalArgumentException("Too many arguments.");
    }

}
