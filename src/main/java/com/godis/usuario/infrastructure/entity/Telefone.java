package com.godis.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity  // para apontar para o spring que isso é uma tabela do databases
@Table(name = "telefone") // indicar o nome da tabela
@Builder

public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero" , length = 10)
    private String numero;
    @Column (name = "ddd" , length = 3)
    private String ddd;
}
