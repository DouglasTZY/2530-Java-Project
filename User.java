public class User {
    private String username;
    private String password;
    private String email;

    public User(String u, String p, String e) {
        this.username = u;
        this.password = p;
        this.email = e;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return username + "|" + password + "|" + email;
    }
}
