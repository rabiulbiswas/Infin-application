package com.infin.entity.platform.user;

import com.infin.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Entity
@Table(name = "Platform_User_Detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformUserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String contactAddress;

    @NotBlank
    @Size(max = 255)
    private String uploadedDocument;

    @NotBlank
    @Size(max = 255)
    private String validIdProof;
    private Integer platformManagerId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
