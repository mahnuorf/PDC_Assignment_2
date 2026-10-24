public class ReutersQuoteEvent extends TradeEvent {
    private final double bidPrice;
    private final double askPrice;

    public ReutersQuoteEvent(
            String senderId,
            long timestamp,
            double basePrice,
            double bidPrice,
            double askPrice) {

        super(senderId, timestamp, basePrice);
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }
}