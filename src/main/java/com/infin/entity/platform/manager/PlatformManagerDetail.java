package com.infin.entity.platform.manager;

import com.infin.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Entity
@Table(name = "Platform_Manager_Detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformManagerDetail {
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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
