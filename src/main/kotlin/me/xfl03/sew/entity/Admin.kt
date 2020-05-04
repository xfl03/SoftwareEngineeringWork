package me.xfl03.sew.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Admin(
    @Id
    val id: Long = 0,
    @Column
    val password: String = ""
)

/*
public class Admin {
    public Admin(...) {
        ...
    }
    public long id;
    public String password;
}
 */