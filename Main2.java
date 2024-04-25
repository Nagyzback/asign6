package pack2;
class WeatherData {
    private String location;
    private double temperature;
    private String description;

    public WeatherData(String location, double temperature, String description) {
        this.location = location;
        this.temperature = temperature;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }
}

interface WeatherAdapter {
    WeatherData fetchWeather(String location);
}

class WeatherAPI1Adapter implements WeatherAdapter {
    private WeatherAPI1 weatherAPI1;

    public WeatherAPI1Adapter(WeatherAPI1 weatherAPI1) {
        this.weatherAPI1 = weatherAPI1;
    }

    @Override
    public WeatherData fetchWeather(String location) {
        WeatherInfo info = weatherAPI1.getWeatherInfo(location);
        return new WeatherData(location, info.getTemperature(), info.getDescription());
    }
}

class WeatherAPI2Adapter implements WeatherAdapter {
    private WeatherAPI2 weatherAPI2;

    public WeatherAPI2Adapter(WeatherAPI2 weatherAPI2) {
        this.weatherAPI2 = weatherAPI2;
    }

    @Override
    public WeatherData fetchWeather(String location) {
        WeatherInfo info = weatherAPI2.getWeather(location);
        return new WeatherData(location, info.getTemperature(), info.getDescription());
    }
}

class WeatherAPI1 {
    public WeatherInfo getWeatherInfo(String location) {
        // Dummy implementation for demonstration
        return new WeatherInfo(25.5, "Sunny");
    }
}

class WeatherAPI2 {
    public WeatherInfo getWeather(String location) {
        // Dummy implementation for demonstration
        return new WeatherInfo(20.0, "Cloudy");
    }
}

class WeatherInfo {
    private double temperature;
    private String description;

    public WeatherInfo(double temperature, String description) {
        this.temperature = temperature;
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }
}

public class Main2 {
    public static void main(String[] args) {
        WeatherAdapter adapter1 = new WeatherAPI1Adapter(new WeatherAPI1());
        WeatherAdapter adapter2 = new WeatherAPI2Adapter(new WeatherAPI2());

        WeatherData data1 = adapter1.fetchWeather("New York");
        WeatherData data2 = adapter2.fetchWeather("London");

        System.out.println("Weather in New York: " + data1.getDescription() + ", Temperature: " + data1.getTemperature() + "°C");
        System.out.println("Weather in London: " + data2.getDescription() + ", Temperature: " + data2.getTemperature() + "°C");
    }
}
