package com.infin.entity.professional.admin;

import com.infin.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Professional_Admin_Detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalAdminDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 20)
    private String membershipNumber;

    @NotBlank
    @Size(max = 255)
    private String contactAddress;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
