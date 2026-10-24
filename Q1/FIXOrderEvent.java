public class FIXOrderEvent extends TradeEvent {
    private final String clOrdId;

    public FIXOrderEvent(
            String senderId,
            long timestamp,
            double basePrice,
            String clOrdId) {

        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }

    @Override
    public void dispatch() {
        double fee = basePrice * 0.0015;
        double total = basePrice + fee;

        System.out.printf(
            "FIX BUY | sender=%s | order=%s | timestamp=%d%n",
            senderId, clOrdId, timestamp
        );

        System.out.printf(
            "Base price: %.2f | Exchange fee (0.15%%): %.2f | Total: %.2f%n",
            basePrice, fee, total
        );
    }
}