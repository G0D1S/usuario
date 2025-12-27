package com.godis.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity  // para apontar para o spring que isso é uma tabela do databases
@Table(name = "usuario") // indicar o nome da tabela, caso nao coloque usa o nome da class
@Builder

public class Usuario implements UserDetails {

    @Id //nosso indentificador unico esse indentificador é o ID da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) //O id sera gerado automaticamente por essa linha
    private Long id;
    @Column(name = "nome" , length = 100) //identifca a coluna e o tamanho
    private String nome;
    @Column(name = "email" , length = 100)
    private String email;
    @Column(name = "senha")
    private String senha;

    @OneToMany (cascade = CascadeType.ALL) //um usuario p/ mts, @OneToOne = um usuario p/ um
    @JoinColumn (name = "usuario_id" , referencedColumnName = "id") // relacao de end/nome
    private List<Endereco> enderecos; //apontando a tabela Endereço

    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id" , referencedColumnName = "id")
    private List<Telefone> telefones; //apontando a tabela de telefone

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
