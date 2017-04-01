package org.neosoft.com.d8challenge;

/**
 * Created by Neyomal on 3/29/2017.
 */

public class Person {
    private String username;
    private String password;

    public Person(String firstname, String lastname) {
        this.username = firstname;
        this.password = lastname;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
