package mg.itu.ticketing.utils;

import java.math.BigDecimal;

public final class ApplicationConstants {

    private ApplicationConstants() { }

    public static final class Views {
        public static final String FRONTOFFICE_PREFIX = "/views/frontoffice/";
        public static final String BACKOFFICE_PREFIX  = "/views/backoffice/";

        private Views() { }
    }

    public static final class BigDecimals {
        public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

        private BigDecimals() { }
    }
}
