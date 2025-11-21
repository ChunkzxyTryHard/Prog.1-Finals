public class User {
    private String username;
    private String password;
    private int age;
    private String phone;
    private String address;

    public User(String username, String password, int age, String phone, String address) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.phone = phone;
        this.address = address;
    }

    public boolean checkLogin(String u, String p) {
        return username.equals(u) && password.equals(p);
    }

    public String getUsername() { return username; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}
