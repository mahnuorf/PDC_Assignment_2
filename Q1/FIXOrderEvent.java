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
}