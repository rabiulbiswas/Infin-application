package com.infin.entity;
import com.infin.entity.audit.DateAudit;
import com.infin.entity.client.ClientAdminDetail;
import com.infin.entity.professional.admin.ProfessionalAdminDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    private String mobile;

    @ColumnDefault("0")
    private Long createdBy;

    @ColumnDefault("0")
    private Long updatedBy;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Long verified;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
    private ProfessionalAdminDetail professionalAdminDetails;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
    private ClientAdminDetail ClientAdminDetails;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    public void User(String name, String email, String password, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;

        User(password);
    }

    public void User(String password){
        this.password = password;
    }
}
