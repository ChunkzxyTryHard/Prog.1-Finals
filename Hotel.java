public class Hotel {
    private String roomType;
    private String description;
    private double price;
    private int availableRooms;

    public Hotel(String roomType, String description, double price, int availableRooms) {
        this.roomType = roomType;
        this.description = description;
        this.price = price;
        this.availableRooms = availableRooms;
    }

    public boolean bookRoom(int rooms) {
        if (rooms > 0 && rooms <= availableRooms) {
            availableRooms -= rooms;
            return true;
        }
        return false;
    }

    public double calculateTotal(int rooms) {
        return rooms * price;
    }

    public String getRoomInfo() {
        return roomType + " - ₱" + price + "\n" + description +
                "\nAvailable: " + availableRooms + " rooms\n";
    }

    // Getters
    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
    public int getAvailableRooms() { return availableRooms; }
}