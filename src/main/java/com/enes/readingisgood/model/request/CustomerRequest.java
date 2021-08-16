package com.enes.readingisgood.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "common.required.field")
    private String name;

    @NotBlank(message = "common.required.field")
    private String surname;

    @NotBlank(message = "common.required.field")
    @Email(message = "common.email.invalid")
    private String email;

    @NotBlank(message = "common.required.field")
    private String username;

    @NotBlank(message = "common.required.field")
    private String password;

}
