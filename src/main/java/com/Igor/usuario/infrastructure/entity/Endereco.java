package com.Igor.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

//anotacoes do lombok para utilizar o get, set e constructor sem precisar escrever eles.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity                          //apontar p/ o spring q essa classe é uma tabela
@Table(name = "endereco")         //indica o nome da tabela


public class Endereco {
    @Id                                                               //Marca qual atributo é a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)               //reponsavel por criar automaricamente o ID unico
    private Long id;

    @Column(name = "rua")
    private String rua;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "complemento", length = 15)
    private String complemento;

    @Column(name = "cidade", length = 150)
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    @Column(name = "cep", length = 9)
    private String cep;



}
