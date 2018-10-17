package hu.galzol.sainsbury.cli;

import hu.galzol.sainsbury.document.UrlSiteReader;
import hu.galzol.sainsbury.scraper.ProductSummary;
import org.json.JSONObject;

public class ProductSiteScraper {

    public static void main(String ... args) {
        check(args);
        hu.galzol.sainsbury.scraper.ProductSiteScraper scraper = new hu.galzol.sainsbury.scraper.ProductSiteScraper(new UrlSiteReader());
        ProductSummary productSummary = scraper.getAllProducts(args[0]);
        JSONObject jsonObject = new JSONObject(productSummary);
        System.out.println(jsonObject.toString(4));
    }

    private static void check(String ... args) {
        if (args.length < 1) exit("Required site URL argument.");
        if (args.length > 1) exit("Too many arguments.");
    }

    private static void exit(String errorMsg) {
        String usage = "USAGE: ProductSiteScraper <url>";
        System.err.print(usage + "\n\nERROR: " + errorMsg);
        System.exit(1);
    }

}
