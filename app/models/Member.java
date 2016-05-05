package models;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public String id;

    public String name;
    public String email;
    public String studentEmail;
    public String password;
}
