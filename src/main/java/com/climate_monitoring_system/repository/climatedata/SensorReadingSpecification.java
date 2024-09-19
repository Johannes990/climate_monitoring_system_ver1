package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.Sensor;
import com.climate_monitoring_system.domain.climatedata.SensorReading;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;

public class SensorReadingSpecification {
    public static Specification<SensorReading> hasSensor(Sensor sensor) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("sensor"), sensor);
    }

    public static Specification<SensorReading> temperatureBelow(float temperature) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("temperature"), temperature);
    }

    public static Specification<SensorReading> temperatureAbove(float temperature) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("temperature"), temperature);
    }

    public static Specification<SensorReading> temperatureBetween(float temperatureLow, float temperatureHigh) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("temperature"), temperatureLow, temperatureHigh);
    }

    public static Specification<SensorReading> relHumidityBelow(float humidity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("relHumidity"), humidity);
    }

    public static Specification<SensorReading> relHumidityAbove(float humidity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("relHumidity"), humidity);
    }

    public static Specification<SensorReading> relHumidityBetween(float humidityLow, float humidityHigh) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("relHumidity"), humidityLow, humidityHigh);
    }

    public static Specification<SensorReading> readingTimeBefore(Timestamp timestamp) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("readingTime"), timestamp);
    }

    public static Specification<SensorReading> readingTimeAfter(Timestamp timestamp) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("readingTime"), timestamp);
    }

    public static Specification<SensorReading> readingTimeBetween(Timestamp timestampAfter, Timestamp timestampBefore) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("readingTime"), timestampAfter, timestampBefore);
    }
}
