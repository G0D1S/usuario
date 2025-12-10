package com.godis.usuario.infrastructure.repository;

//IMPORTANTE: Todas nossas entidades precisam de um repository, havendo ou nao relacionamento
//É obrigatorio criar um repository para cada entidade


import com.godis.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //esta herdando para usar apenas os metodos necessario
//se eu quiser usar alguns metodos seria "implements" porem teria q implementar o metodo
    boolean existsByEmail(String email);   //escript do jpa

    Optional<Usuario> findByEmail(String email); //trata o retrono nulo

    @Transactional
    void deleteByEmail(String email);
}
