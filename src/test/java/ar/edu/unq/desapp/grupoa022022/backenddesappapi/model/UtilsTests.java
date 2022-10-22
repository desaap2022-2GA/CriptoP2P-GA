package ar.edu.unq.desapp.grupoa022022.backenddesappapi.model;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.HelperDTO;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ExceptionsUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UtilsTests {

    HelperDTO helperDTO = new HelperDTO();

    @Test
    void firstArgumentIsNullReturnFalse() throws ExceptionsUser {

        assertFalse(helperDTO.firstNotNullAndFirstAndSecondNotEquals(null, "0"));
    }

    @Test
    void firstArgumentNotNullButEqualsToSecondReturnFalse() throws ExceptionsUser {

        assertFalse(helperDTO.firstNotNullAndFirstAndSecondNotEquals("0", "0"));
    }

    @Test
    void firstArgumentNotNullAndNotEqualsToSecondReturnTrue() throws ExceptionsUser {

        assertTrue(helperDTO.firstNotNullAndFirstAndSecondNotEquals("0", "1"));
    }
}
