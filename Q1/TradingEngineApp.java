public class TradingEngineApp {
    public static void main(String[] args) {
        long now = System.currentTimeMillis();

        TradeEvent order = new FIXOrderEvent(
            "FIX-Gateway", now, 1000.0, "BUY-001"
        );

        TradeEvent quote = new ReutersQuoteEvent(
            "Reuters-Gateway", now, 1000.0, 999.0, 1001.0
        );

        order.dispatch();
        quote.dispatch();
    }
}