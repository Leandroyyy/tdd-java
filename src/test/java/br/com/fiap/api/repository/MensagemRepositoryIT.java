package br.com.fiap.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import br.com.fiap.api.model.Mensagem;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class MensagemRepositoryIT {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Test
    void devePermitirCriarTabela() {
        var totalDeRegistros = mensagemRepository.count();
        assertThat(totalDeRegistros).isNotNegative();

    }

    @Test
    void devePermitirRegistrarMensagem() {
        var id = UUID.randomUUID();
        var mensagem = gerarMensagem();
        mensagem.setId(id);

        var mensagemRecebida = mensagemRepository.save(mensagem);

        assertThat(mensagemRecebida)
                .isInstanceOf(Mensagem.class)
                .isNotNull();

        assertThat(mensagemRecebida.getId()).isEqualTo(id);
        assertThat(mensagemRecebida.getConteudo()).isEqualTo(mensagem.getConteudo());
        assertThat(mensagemRecebida.getUsuario()).isEqualTo(mensagem.getUsuario());

    }

    @Test
    void devePermitirBuscarMensagem() {
        fail("teste não implementado");
    }

    @Test
    void devePermitirRemoverMensagem() {
        fail("teste não implementado");
    }

    @Test
    void devePermitirListarMensagens() {
        fail("teste não implementado");
    }

    private Mensagem gerarMensagem() {
        return Mensagem.builder()
                .id(UUID.randomUUID())
                .usuario("Jose")
                .conteudo("conteudo da mensagem")
                .build();
    }
}
