package com.godis.usuario.controlller;

import com.godis.usuario.business.UsuarioService;
import com.godis.usuario.business.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

RestController
@RequestMapping ("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@ResponseBody UsuarioDTO usuarioDTO) {

        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }
}
