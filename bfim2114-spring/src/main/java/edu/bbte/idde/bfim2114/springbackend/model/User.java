package edu.bbte.idde.bfim2114.springbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {

    @Column(unique = true, name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Collection<HardwarePart> hardwareParts;

    public void addHardwarePart(HardwarePart hardwarePart) {
        hardwareParts.add(hardwarePart);
    }

    public void removeHardwarePart(HardwarePart hardwarePart) {
        hardwareParts.remove(hardwarePart);
    }
}
