package hu.galzol.sainsbury.scraper;

import java.math.BigDecimal;

public class ProductTotal {

    private final BigDecimal gross;
    private final BigDecimal vat;

    public ProductTotal(BigDecimal gross, BigDecimal vat) {
        this.gross = gross;
        this.vat = vat;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public BigDecimal getVat() {
        return vat;
    }

}
