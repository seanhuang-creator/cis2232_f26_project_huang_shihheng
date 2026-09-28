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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TacklePenaltyServiceTest {

    @Mock
    private TacklePenaltyRepository repo;

    @InjectMocks
    private TacklePenaltyService service;

    @Test
    void create_calculatesImpactScoreAndReturnsSavedPenalty() {
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

        when(repo.save(any(TacklePenalty.class))).thenAnswer(invocation -> {
            TacklePenalty saved = invocation.getArgument(0);
            saved.setId(1);
            return saved;
        });

        ResponseEntity<TacklePenalty> response = service.create(input);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals(20.0, response.getBody().getImpactScore());
        assertEquals("Holland College Hurricanes", response.getBody().getHomeTeam());

        ArgumentCaptor<TacklePenalty> captor =
                ArgumentCaptor.forClass(TacklePenalty.class);
        verify(repo).save(captor.capture());
        assertEquals(20.0, captor.getValue().getImpactScore());
    }
}
