package models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 255,nullable = false,unique = true)
    private String name;
}
