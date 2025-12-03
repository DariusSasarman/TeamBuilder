package persistencepackage;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String passwordHash;
    private Integer UID;

    public User(String username, String passwordHash, Integer UID) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.UID = UID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Integer getUID() {
        return UID;
    }

    public void setUID(Integer UID) {
        this.UID = UID;
    }
}