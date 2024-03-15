package br.com.ciamed.connectciamed.ConnectCiamed.controller.interplayers;

import br.com.ciamed.connectciamed.ConnectCiamed.model.tables.interplayers.InterplayersHistoricoModel;
import br.com.ciamed.connectciamed.ConnectCiamed.repository.interplayers.InterplayersHistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class InterplayersHistoricoController {
    @Autowired
    private InterplayersHistoricoRepository histRepo;

    @GetMapping("/listarInterplayersHistorico")
    public ResponseEntity<List<InterplayersHistoricoModel>> listarInterplayersHistorico(){
        List<InterplayersHistoricoModel> dados = histRepo.findAll();
        return ResponseEntity.ok(dados);
    }

}
