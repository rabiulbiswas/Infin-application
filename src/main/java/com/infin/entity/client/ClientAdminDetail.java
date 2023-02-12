package com.infin.entity.client;

import com.infin.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Client_Admin_Detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAdminDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String companyName;

    @NotBlank
    @Size(max = 40)
    private String panNumber;

    @NotBlank
    @Size(max = 255)
    private String gstNumber;

    @NotBlank
    @Size(max = 40)
    private String businessType;

    @NotBlank
    @Size(max = 255)
    private String communicationAddress;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
