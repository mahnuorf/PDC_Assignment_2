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
        @Override
    public void dispatch() {
        double spread = askPrice - bidPrice;

        System.out.printf(
            "REUTERS QUOTE | sender=%s | timestamp=%d | reference=%.2f%n",
            senderId, timestamp, basePrice
        );

        System.out.printf(
            "Bid: %.2f | Ask: %.2f | Spread: %.2f%n",
            bidPrice, askPrice, spread
        );
    }
}