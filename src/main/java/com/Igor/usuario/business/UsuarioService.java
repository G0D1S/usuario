package com.Igor.usuario.business;


import com.Igor.usuario.business.converter.UsuarioConverter;
import com.Igor.usuario.business.dto.UsuarioDTO;
import com.Igor.usuario.infrastructure.entity.Usuario;
import com.Igor.usuario.infrastructure.exceptions.ConflictException;
import com.Igor.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario (UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );

    }

    public void emailExiste(String email) {
        try {                                                                                                           //bloco de tratamento de excessao -> verificando se tera um email existente
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("email ja cadastrado" + email);                                             //throw new = lançar o erro (exception) automaticamente e para o progrma, o "new" é pq exception é um objeto
            }
        } catch (
                ConflictException e) {                                                                                   // o que fazer se der erro

            throw new ConflictException("email ja cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {                                                            //responsavel so por chamar o metodo na respository -> banco de dados
        return usuarioRepository.existsByEmail(email);
    }


}
