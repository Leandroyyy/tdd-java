package br.com.fiap.api.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.api.model.Mensagem;

public class MensagemRepositoryTest {

    @Mock
    private MensagemRepository mensagemRepository;

    AutoCloseable openMock;

    @BeforeEach
    void setup() {
        openMock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMock.close();
    }

    @Test
    void devePermitirRegistrarMensagem() {
        var mensagem = gerarMensagem();

        when(mensagemRepository.save(any(Mensagem.class))).thenReturn(mensagem);

        var mensagemArmazenada = mensagemRepository.save(mensagem);

        Assertions.assertThat(mensagemArmazenada).isNotNull().isEqualTo(mensagem);
        verify(mensagemRepository, times(1)).save(any(Mensagem.class));
    }

    @Test
    void devePermitirBuscarMensagem() {
        var id = UUID.randomUUID();
        var mensagem = gerarMensagem();
        mensagem.setId(id);

        when(mensagemRepository.findById(any(UUID.class))).thenReturn(Optional.of(mensagem));

        var mensagemRecebidaOpcional = mensagemRepository.findById(id);

        Assertions.assertThat(mensagemRecebidaOpcional)
                .isPresent()
                .containsSame(mensagem);

        mensagemRecebidaOpcional.ifPresent(mensagemRecebida -> {
            Assertions.assertThat(mensagemRecebida.getId()).isEqualTo(mensagem.getId());
            Assertions.assertThat(mensagemRecebida.getConteudo()).isEqualTo(mensagem.getConteudo());
        });

        verify(mensagemRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void devePermitirRemoverMensagem() {
        var id = UUID.randomUUID();

        doNothing().when(mensagemRepository).deleteById(any(UUID.class));

        mensagemRepository.deleteById(id);

        verify(mensagemRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void devePermitirListarMensagens() {

        var mensagem1 = gerarMensagem();
        var mensagem2 = gerarMensagem();
        var listaMensagens = Arrays.asList(mensagem1, mensagem2);

        when(mensagemRepository.findAll()).thenReturn(listaMensagens);

        var mensagensRecebidas = mensagemRepository.findAll();

        Assertions.assertThat(mensagensRecebidas)
                .hasSize(2)
                .containsExactlyInAnyOrder(mensagem1, mensagem2);

        verify(mensagemRepository, times(1)).findAll();
    }

    private Mensagem gerarMensagem() {
        return Mensagem.builder()
                .id(UUID.randomUUID())
                .usuario("Jose")
                .conteudo("conteudo da mensagem")
                .build();
    }
}
