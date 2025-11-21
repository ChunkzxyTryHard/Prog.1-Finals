import javax.swing.*;
import java.util.ArrayList;

public class HotelBookingApp {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Hotel> rooms = new ArrayList<>();




        // Hotel Transylvania Room Options
        rooms.add(new Hotel("Standard Queen", "One queen bed, ideal for 1-2 adults.", 2000, 10));
        rooms.add(new Hotel("Standard Twin", "Two single beds, perfect for small groups.", 2500, 8));
        rooms.add(new Hotel("Standard Double", "One double bed.", 2800, 6));
        rooms.add(new Hotel("Deluxe Room", "Larger room with one queen and one single bed (up to 3 adults).", 3100, 5));
        rooms.add(new Hotel("Family Room", "One single and two queen beds (great for families or groups).", 3700, 4));

        // === MAIN MENU LOOP ===
        while (true) {
            String[] options = {"Register", "Login", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to Hotel Transylvania Booking System",
                    "Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            if (choice == 0) { // Register
                register(users);
            } else if (choice == 1) { // Login
                User loggedUser = login(users);
                if (loggedUser != null) {
                    userMenu(loggedUser, rooms);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Thank you for visiting Hotel Transylvania!");
                break;
            }
        }
    }

    // ===== REGISTER =====
    private static void register(ArrayList<User> users) {
    String ageInput = JOptionPane.showInputDialog("Enter your age:");
if (ageInput == null || ageInput.trim().isEmpty()) return;

int age; // declare as int
try {
    age = Integer.parseInt(ageInput.trim()); // convert String to int
    if (age <= 18) {
        JOptionPane.showMessageDialog(null,
                "Registration failed.\nYou must be 18 years old and above to use this system.");
        return;
    }
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(null, "Please enter a valid number for age.");
    return;
}
    
   String numberInput = JOptionPane.showInputDialog("Enter your 11-digit phone number:");
      if (numberInput == null || numberInput.trim().isEmpty()) return;

numberInput = numberInput.trim();


      if (!numberInput.matches("\\d{11}")) {
    JOptionPane.showMessageDialog(null,
            "Please enter a valid 11-digit phone number.");
    return;
}


    JOptionPane.showMessageDialog(null,
        "Phone number accepted:\n" + numberInput);
     
   

    String address = JOptionPane.showInputDialog("Enter address:");
    if (address == null || address.trim().isEmpty()) return;
    

    String username = JOptionPane.showInputDialog("Enter username:");
    if (username == null || username.trim().isEmpty()) return;
    

    for (User u : users) {
        if (u.getUsername().equals(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists!");
            return;
        }
    }

    String password = JOptionPane.showInputDialog("Enter password:");
    if (password == null || password.trim().isEmpty()) return;

 users.add(new User(username, password, age, numberInput, address));

    JOptionPane.showMessageDialog(null, "Registration successful!");
}

    // ===== LOGIN =====
    private static User login(ArrayList<User> users) {
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        if (username == null || password == null) return null;

        for (User u : users) {
            if (u.checkLogin(username, password)) {
                JOptionPane.showMessageDialog(null, "Welcome, " + u.getUsername() + "!");
                return u;
            }
        }
        JOptionPane.showMessageDialog(null, "Invalid username or password.");
        return null;
    }
    // ===== USER MENU =====
private static void userMenu(User user, ArrayList<Hotel> rooms) {
    while (true) {
        String[] userOptions = {"View Rooms", "Book a Room", "User Info", "Logout"};
        int action = JOptionPane.showOptionDialog(
                null,
                "Welcome, " + user.getUsername() + "!\nWhat would you like to do?",
                "User Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, userOptions, userOptions[0]);

        if (action == 0) {
            viewRooms(rooms);
        } else if (action == 1) {
            bookRoom(rooms);
        } else if (action == 2) {
            showUserInfo(user);
        } else {
            JOptionPane.showMessageDialog(null, "You have logged out.");
            return;
        }
    }
}


    // ===== VIEW ROOMS =====
    private static void viewRooms(ArrayList<Hotel> rooms) {
        StringBuilder sb = new StringBuilder("Hotel Transylvania Room Options \n\n");
        sb.append("Standard Rooms:\n----------------------------\n");
        for (Hotel h : rooms) {
            if (h.getRoomType().startsWith("Standard"))
                sb.append("• ").append(h.getRoomInfo()).append("\n");
        }
        sb.append("\nDeluxe and Family Rooms:\n----------------------------\n");
        for (Hotel h : rooms) {
            if (!h.getRoomType().startsWith("Standard"))
                sb.append("• ").append(h.getRoomInfo()).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

// ==== USER INFO ====
private static void showUserInfo(User user) {
    StringBuilder msg = new StringBuilder();

    msg.append("===== USER INFORMATION =====\n\n");
    msg.append("Username: ").append(user.getUsername()).append("\n");
    msg.append("Age: ").append(user.getAge()).append("\n");
    msg.append("Phone: ").append(user.getPhone()).append("\n");
    msg.append("Address: ").append(user.getAddress()).append("\n\n");

    JOptionPane.showMessageDialog(null, msg.toString());
}



     // ===== BOOK ROOM (Dropdown List + Payment) =====
private static void bookRoom(ArrayList<Hotel> rooms) {
    try {
        // Create dropdown (combo box)
        JComboBox<String> comboBox = new JComboBox<>();
        for (Hotel h : rooms) {
            comboBox.addItem(h.getRoomType() + " (₱" + h.getPrice() + ")");
        }

        int result = JOptionPane.showConfirmDialog(
                null,
                comboBox,
                "Select a room type:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) return;

        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex == -1) return;

        Hotel selected = rooms.get(selectedIndex);

        String input = JOptionPane.showInputDialog("How many rooms would you like to book?");
        if (input == null) return;

        int count = Integer.parseInt(input.trim());
        if (!selected.bookRoom(count)) {
            JOptionPane.showMessageDialog(null, "Not enough rooms available.");
            return;
        }

        double total = selected.calculateTotal(count);

        // ===== PAYMENT METHOD =====
        String[] paymentOptions = {"Credit/Debit Card", "G-Cash", "PayMaya","Cash"};
        JComboBox<String> paymentBox = new JComboBox<>(paymentOptions);

        int payChoice = JOptionPane.showConfirmDialog(
                null,
                paymentBox,
                "Select Payment Method:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (payChoice != JOptionPane.OK_OPTION) return;

        String method = (String) paymentBox.getSelectedItem();
        String details = "";

        switch (method) {
            case "Credit/Debit Card":
                String card = JOptionPane.showInputDialog("Enter 16-digit Card Number:");
                if (card == null || !card.matches("\\d{16}")) {
                    JOptionPane.showMessageDialog(null, "Invalid card number!");
                    return;
                }

                String expiry = JOptionPane.showInputDialog("Enter Expiry Date (MM/YY):");
                if (expiry == null || !expiry.matches("\\d{2}/\\d{2}")) {
                    JOptionPane.showMessageDialog(null, "Invalid expiry date!");
                    return;
                }

                String cvv = JOptionPane.showInputDialog("Enter CVV (3 digits):");
                if (cvv == null || !cvv.matches("\\d{3}")) {
                    JOptionPane.showMessageDialog(null, "Invalid CVV!");
                    return;
                }

                details = "Card: **** **** **** " + card.substring(12) + "\nExpiry: " + expiry;
                break;

            case "G-Cash":
                String gcash = JOptionPane.showInputDialog("Enter G-Cash Number (11 digits):");
                if (gcash == null || !gcash.matches("\\d{11}")) {
                    JOptionPane.showMessageDialog(null, "Invalid number!");
                    return;
                }
                details = "G-Cash Number: " + gcash;
                break;

            case "PayMaya":
                String paymaya = JOptionPane.showInputDialog("Enter PayMaya Number (11 digits):");
                if (paymaya == null || !paymaya.matches("\\d{11}")) {
                    JOptionPane.showMessageDialog(null, "Invalid number!");
                    return;
                }
                details = "PayMaya Number: " + paymaya;
                break;
        }
        

        
    // ==== DATE RESERVATION ====

      JPanel datePanel = new JPanel();

            // Month dropdown
       String[] months = new String[12];
       for (int i = 1; i <= 12; i++) {
          months[i - 1] = String.format("%02d", i);
  }
       JComboBox<String> monthBox = new JComboBox<>(months);

           // Day dropdown
       String[] days = new String[31];
       for (int i = 1; i <= 31; i++) {
    days[i - 1] = String.format("%02d", i);
   }
        JComboBox<String> dayBox = new JComboBox<>(days);

           // Year dropdown (2024–2035)
        String[] years = new String[12];
        int startYear = 2024;
        for (int i = 0; i < 12; i++) {
        years[i] = String.valueOf(startYear + i);
  }
        JComboBox<String> yearBox = new JComboBox<>(years);

// Panel layout
        datePanel.add(new JLabel("Month:"));
        datePanel.add(monthBox);
        datePanel.add(new JLabel("Day:"));
        datePanel.add(dayBox);
        datePanel.add(new JLabel("Year:"));
        datePanel.add(yearBox);

// Show date selection dialog
         int dateResult = JOptionPane.showConfirmDialog(
        null,
        datePanel,
        "Select Reservation Date",
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE
         );

if (dateResult != JOptionPane.OK_OPTION) {
    JOptionPane.showMessageDialog(null, "Reservation canceled.");
    return;
  }

String month = monthBox.getSelectedItem().toString();
String day = dayBox.getSelectedItem().toString();
String year = yearBox.getSelectedItem().toString();
String reservationDate = month + "/" + day + "/" + year;

// Show selected date
JOptionPane.showMessageDialog(null,
        "Reservation Date: " + reservationDate);

        JOptionPane.showMessageDialog(null,
                "Booking Successful!\n\nRoom: " + selected.getRoomType() +
                "\nRooms Booked: " + count +
                "\nTotal: ₱" + total +
                "\nPayment Method: " + method +
                "\n" + details +
                "\nReservation Date:" + reservationDate);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Invalid input.");
    }
}  
}  
