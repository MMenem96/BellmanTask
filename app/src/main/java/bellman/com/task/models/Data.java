
package bellman.com.task.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    @SerializedName("attractions")
    @Expose
    private List<Attraction> attractions = null;
    @SerializedName("events")
    @Expose
    private List<Object> events = null;
    @SerializedName("hot_spots")
    @Expose
    private List<HotSpot> hotSpots = null;
    private final static long serialVersionUID = -8340802497128911514L;

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param hotSpots
     * @param attractions
     * @param events
     */
    public Data(List<Attraction> attractions, List<Object> events, List<HotSpot> hotSpots) {
        super();
        this.attractions = attractions;
        this.events = events;
        this.hotSpots = hotSpots;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    public List<Object> getEvents() {
        return events;
    }

    public void setEvents(List<Object> events) {
        this.events = events;
    }

    public List<HotSpot> getHotSpots() {
        return hotSpots;
    }

    public void setHotSpots(List<HotSpot> hotSpots) {
        this.hotSpots = hotSpots;
    }

}
