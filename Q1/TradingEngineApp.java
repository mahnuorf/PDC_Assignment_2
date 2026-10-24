public class TradingEngineApp {
    public static void main(String[] args) {
        long now = System.currentTimeMillis();

        TradeEvent[] events = {
            new TradeEvent("Event-Gateway", now, 1000.0),

            new FIXOrderEvent(
                "FIX-Gateway", now, 1000.0, "BUY-001"
            ),

            new ReutersQuoteEvent(
                "Reuters-Gateway", now, 1000.0, 999.0, 1001.0
            ),

            new FIXOrderEvent(
                "FIX-Gateway", now, 2000.0, "BUY-002"
            )
        };

        for (TradeEvent event : events) {
            System.out.println(
                "Dispatching runtime type: "
                + event.getClass().getSimpleName()
            );

            event.dispatch();
            System.out.println();
        }

        System.out.println(
            "Known checks: BUY-001 fee=1.50, total=1001.50; spread=2.00"
        );

        System.out.println(
            "Known checks: BUY-002 fee=3.00, total=2003.00"
        );
    }
}