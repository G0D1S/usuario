package com.godis.usuario.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity  // para apontar para o spring que isso é uma tabela do databases
@Table(name = "endereco") // indicar o nome da tabela

public class Endereco {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rua")
    private String rua;
    @Column(name = "numero")
    private long numero;
    @Column(name = "complemento" , length = 20)
    private String complemento;
    @Column(name = "cidade" , length = 150)
    private String cidade;
    @Column(name = "estado" , length = 2)
    private String estado;
    @Column(name = "cep" , length = 9)
    private String cep;

 // as entity sao as classes que representam nossas tabelas
    // os repository sao as classes que fazem a comunicacao com nosso banco de dados

}
