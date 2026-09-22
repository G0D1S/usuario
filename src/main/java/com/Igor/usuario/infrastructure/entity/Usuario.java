package com.Igor.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//anotacoes do lombok para utilizar o get, set e constructor sem precisar escrever eles.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity                          //apontar p/ o spring q essa classe é uma tabela
@Table(name = "usuario")         //indica o nome da tabela

public class Usuario implements UserDetails {

    @Id                                                               //Marca qual atributo é a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)               //reponsavel por criar automaricamente o ID unico
    private Long id;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "senha")
    private String senha;

    //isso esta criando os relacionamentos unidirecional, onde um usuariopode ter varios end, numeros
    @OneToMany(cascade = CascadeType.ALL)      //cascadetype.all qunado eu excluir, vai excluir tudo
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")      //busca o nome na coluna de acordo com o ID chamado
    private List<Endereco> enderecos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefones;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
