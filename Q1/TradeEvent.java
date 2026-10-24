public class TradeEvent {
    protected String senderId;
    protected long timestamp;
    protected double basePrice;

    public TradeEvent(String senderId, long timestamp, double basePrice) {
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.basePrice = basePrice;
    }

    public void dispatch() {
        System.out.printf(
            "Generic event | sender=%s | timestamp=%d | price=%.2f%n",
            senderId, timestamp, basePrice
        );
    }
}