package com.Igor.usuario.business;


import com.Igor.usuario.business.converter.UsuarioConverter;
import com.Igor.usuario.business.dto.EnderecoDTO;
import com.Igor.usuario.business.dto.TelefoneDTO;
import com.Igor.usuario.business.dto.UsuarioDTO;
import com.Igor.usuario.infrastructure.entity.Endereco;
import com.Igor.usuario.infrastructure.entity.Telefone;
import com.Igor.usuario.infrastructure.entity.Usuario;
import com.Igor.usuario.infrastructure.exceptions.ConflictException;
import com.Igor.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.Igor.usuario.infrastructure.repository.EnderecoRepository;
import com.Igor.usuario.infrastructure.repository.TelefoneRepository;
import com.Igor.usuario.infrastructure.repository.UsuarioRepository;
import com.Igor.usuario.infrastructure.security.JwtUtil;
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
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try{
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.findByEmail(email)
                        .orElseThrow(
                () -> new ResourceNotFoundException("email nao encontrado" + email)
                        )
        );
        }catch (ResourceNotFoundException e){
            throw new RuntimeException("email nao encontrado" + email);
        }
    }

    public void deletaUsuarioPorEmail(String email) {

        usuarioRepository.deleteByEmail(email);
    }
    public  UsuarioDTO autalizaDadosUsuario (String token, UsuarioDTO dto){
        //aqui buscamos o email do usuario atraves do token (tirando a obrigatoriedade do usuario passar o email)
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        //criptografia de senha == se for nulo nao vai setar nada
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null );

        //busca os dados do usuario no banco de dados, se nao encontrando da joga a excessao "n encontrado "
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado"));

        //meclou os dados que recebemos na requisicao DTO com os dados no banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        //salvou os dados do usuario convertido e depois pegou o retorno e convertou para usuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id nao encontrado " + idEndereco));


        Endereco endereco= usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atulizaTelefone(Long idTelefone, TelefoneDTO dto ){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id nao encontrado " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado"));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);

    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado"));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        Telefone telefoneEntity = telefoneRepository.save(telefone);
        return usuarioConverter.paraTelefoneDTO(telefoneEntity);
    }

}
