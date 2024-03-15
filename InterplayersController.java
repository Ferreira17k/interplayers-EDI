package br.com.ciamed.connectciamed.ConnectCiamed.controller.interplayers;

import br.com.ciamed.connectciamed.ConnectCiamed.service.interplayers.InterplayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterplayersController {
    @Autowired
    private InterplayersService service;

    @PostMapping("interplayers")
    public ResponseEntity criarArquivoEmInterplayers(@RequestParam Integer unidade, @RequestParam Integer compra, @RequestParam String cookieValue) {
        service.run(unidade, compra, cookieValue);
        // System.out.println(cookieValue);
        return ResponseEntity.ok(compra);
    }



}
