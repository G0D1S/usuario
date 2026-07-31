package com.Igor.usuario.infrastructure.repository;

//camada responsavel por conversar com o banco de dados
//extends = usa quando é necessario , imports = é obrigatorio a usar


import com.Igor.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //criando um script, ira retornar um boolean se o dado passado ja existe no banco de dados
    boolean existsByEmail(String email);

    //validador de dados null
    Optional<Usuario> findByEmail (String email);            //caso a aplicacao busque o email e n encontre, a aplicacao vai quebrar

    @Transactional
    void deleteByEmail(String email);
}


//service é quem faz o meio de campo entre o usuario que acessa a nossa controller via API e o databese via repository
