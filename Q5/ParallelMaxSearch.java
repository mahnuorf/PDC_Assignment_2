package Q5;

public class ParallelMaxSearch extends SensorSearchEngine {
    public ParallelMaxSearch(
            double[] sensorData,
            int startIdx,
            int endIdx) {

        super(sensorData, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        double result = Double.NEGATIVE_INFINITY;

        for (int i = startIdx; i < endIdx; i++) {
            result = Math.max(result, sensorData[i]);
        }

        return result;
    }
}