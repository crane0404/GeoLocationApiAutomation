import java.util.Random;

public class GeolocationTestUtils {

    private static final Random random = new Random();

    public static Object[][] generateRandomCoordinates() {
        double[][] coordinates = {
                {random.nextDouble() * 180 - 90, random.nextDouble() * 360 - 180}, // Random latitude and longitude
                {random.nextDouble() * 180 - 90, random.nextDouble() * 360 - 180}, // Random latitude and longitude
                {random.nextDouble() * 180 - 90, random.nextDouble() * 360 - 180}  // Random latitude and longitude
        };

        Object[][] data = new Object[coordinates.length][];
        for (int i = 0; i < coordinates.length; i++) {
            data[i] = new Object[]{coordinates[i][0], coordinates[i][1]};
        }
        return data;
    }

    public static double generateRandomAccuracy() {
        double minAccuracy = 100000; // Example minimum accuracy
        double maxAccuracy = 500000; // Example maximum accuracy
        return minAccuracy + (maxAccuracy - minAccuracy) * random.nextDouble();
    }

    public static String generateRequestBody(double latitude, double longitude) {
        return "{ " +
                "\"homeMobileCountryCode\":310, " +
                "\"homeMobileNetworkCode\":410, " +
                "\"radioType\":\"gsm\", " +
                "\"carrier\":\"Vodafone\", " +
                "\"considerIp\":true, " +
                "\"location\": { " +
                "\"lat\": " + latitude + ", " +
                "\"lng\": " + longitude + " } " +
                "}";
    }

    public static String generateRequestBodyWithAccuracy(double accuracy) {
        return "{ " +
                "\"homeMobileCountryCode\":310, " +
                "\"homeMobileNetworkCode\":410, " +
                "\"radioType\":\"gsm\", " +
                "\"carrier\":\"Vodafone\", " +
                "\"considerIp\":true, " +
                "\"accuracy\": " + accuracy +
                "}";
    }

    public static String generateInvalidRequestBody() {
        return "{invalidJson}";
    }
}
