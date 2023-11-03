package dev.sergevas.iot.growlabv1.shared.domain;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * An array of sensor readings
 */
public class SensorReadingsType {

    @Valid
    private List<SensorReadingsItemType> sReadings = new ArrayList<SensorReadingsItemType>();

    /**
     * Sensor readings
     **/
    public SensorReadingsType sReadings(List<SensorReadingsItemType> sReadings) {
        this.sReadings = sReadings;
        return this;
    }

    @JsonbProperty("s_readings")
    public List<SensorReadingsItemType> getSReadings() {
        return sReadings;
    }

    public void setSReadings(List<SensorReadingsItemType> sReadings) {
        this.sReadings = sReadings;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensorReadingsType sensorReadingsType = (SensorReadingsType) o;
        return Objects.equals(sReadings, sensorReadingsType.sReadings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sReadings);
    }

    @Override
    public String toString() {

        return "class SensorReadingsType {\n" +
                "    sReadings: " + toIndentedString(sReadings) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
