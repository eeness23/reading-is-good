package com.enes.readingisgood.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "dto.required.field")
    private String name;

    @NotBlank(message = "dto.required.field")
    private String surname;

    @NotBlank(message = "dto.required.field")
    @Email(message = "dto.email.invalid")
    private String email;

    @NotBlank(message = "dto.required.field")
    private String username;

    @NotBlank(message = "dto.required.field")
    private String password;

}
