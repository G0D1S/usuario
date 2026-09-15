package com.Igor.usuario.business.dto;


import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {

    private long id;
    private String numero;
    private String ddd;
}
