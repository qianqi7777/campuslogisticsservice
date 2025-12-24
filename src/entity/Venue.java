package src.entity;

public class Venue {
    private int venueID;
    private String venueName;
    private int capacity;
    private String location;
    private String isAvailable;

    public Venue() {}

    public int getVenueID() { return venueID; }
    public void setVenueID(int venueID) { this.venueID = venueID; }

    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getIsAvailable() { return isAvailable; }
    public void setIsAvailable(String isAvailable) { this.isAvailable = isAvailable; }
}
