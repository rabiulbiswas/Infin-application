package com.infin.dto.professional.manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalManagerRequest {
    private Long professionalManagerDetailId;
    @NotEmpty(message = "Please upload any valid ID proof")
    private String validIdProof;
}
