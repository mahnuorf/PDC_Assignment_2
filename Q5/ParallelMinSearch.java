package Q5;

public class ParallelMinSearch extends SensorSearchEngine {
    public ParallelMinSearch(
            double[] sensorData,
            int startIdx,
            int endIdx) {

        super(sensorData, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        double result = Double.POSITIVE_INFINITY;

        for (int i = startIdx; i < endIdx; i++) {
            result = Math.min(result, sensorData[i]);
        }

        return result;
    }
}