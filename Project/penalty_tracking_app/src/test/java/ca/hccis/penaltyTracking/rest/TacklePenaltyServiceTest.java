package ca.hccis.penaltyTracking.rest;

import ca.hccis.penaltyTracking.jpa.entity.TacklePenalty;
import ca.hccis.penaltyTracking.repositories.TacklePenaltyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TacklePenaltyServiceTest {

    @Mock
    private TacklePenaltyRepository repo;

    @InjectMocks
    private TacklePenaltyService service;

    private TacklePenalty createValidInput() {
        TacklePenalty input = new TacklePenalty();
        input.setHomeTeam("Holland College Hurricanes");
        input.setAwayTeam("Saint John Falcons");
        input.setDate("2026-09-12");
        input.setPenalty("Unnecessary Roughness");
        input.setQuarter(4);
        input.setPenalizedTeam("Saint John Falcons");
        input.setOffendingPlayer(6);
        input.setAgeDivision("AFL");
        input.setReferee("Nathan");
        return input;
    }

    @Test
    void create_valid_returns201AndImpactScore10() {
        TacklePenalty input = createValidInput();
        BindingResult bindingResult = new BeanPropertyBindingResult(input, "tacklePenalty");

        when(repo.save(any(TacklePenalty.class))).thenAnswer(invocation -> {
            TacklePenalty saved = invocation.getArgument(0);
            saved.setId(1);
            return saved;
        });

        ResponseEntity<?> response = service.create(input, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        TacklePenalty body = (TacklePenalty) response.getBody();
        assertEquals(1, body.getId());
        assertEquals(10.0, body.getImpactScore());
        assertEquals("Holland College Hurricanes", body.getHomeTeam());

        ArgumentCaptor<TacklePenalty> captor = ArgumentCaptor.forClass(TacklePenalty.class);
        verify(repo).save(captor.capture());
        assertEquals(10.0, captor.getValue().getImpactScore());
    }

    @Test
    void create_invalidQuarter_returns400AndDoesNotSave() {
        TacklePenalty input = createValidInput();
        input.setQuarter(5);
        BindingResult bindingResult = new BeanPropertyBindingResult(input, "tacklePenalty");

        ResponseEntity<?> response = service.create(input, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(repo, never()).save(any(TacklePenalty.class));
    }

    @Test
    void getAll_and_getById_returns200And404() {
        TacklePenalty first = createValidInput();
        first.setId(1);
        first.setImpactScore(10.0);
        TacklePenalty second = createValidInput();
        second.setId(2);
        second.setPenalty("Holding");
        second.setQuarter(1);
        second.setImpactScore(2.0);

        when(repo.findAll()).thenReturn(List.of(first, second));
        when(repo.findById(1)).thenReturn(Optional.of(first));
        when(repo.findById(9999)).thenReturn(Optional.empty());

        ResponseEntity<List<TacklePenalty>> allResponse = service.getAll();
        assertEquals(HttpStatus.OK, allResponse.getStatusCode());
        assertNotNull(allResponse.getBody());
        assertEquals(2, allResponse.getBody().size());

        ResponseEntity<TacklePenalty> foundResponse = service.getById(1);
        assertEquals(HttpStatus.OK, foundResponse.getStatusCode());
        assertEquals(1, foundResponse.getBody().getId());

        ResponseEntity<TacklePenalty> notFoundResponse = service.getById(9999);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
    }

    @Test
    void delete_found_returns204AndNotFound_returns404() {
        when(repo.existsById(1)).thenReturn(true);
        when(repo.existsById(9999)).thenReturn(false);

        ResponseEntity<Void> deletedResponse = service.delete(1);
        assertEquals(HttpStatus.NO_CONTENT, deletedResponse.getStatusCode());
        verify(repo).deleteById(1);

        ResponseEntity<Void> notFoundResponse = service.delete(9999);
        assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
        verify(repo, never()).deleteById(9999);
    }
}