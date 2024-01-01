package com.olympus.mapper;

import com.olympus.dto.response.CurrentUserProfile;
import com.olympus.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class CurrentUserProfileMapperImplTest {
    @InjectMocks
    private CurrentUserProfileMapperImpl mapper;
    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
    }

    @Test
    void whenMapUserToDto_thenCorrect() {
        CurrentUserProfile dto = mapper.toDTO(user);

        assertEquals(user.getId(), dto.getId());
    }

    @Test
    void whenMapNullUser_thenNull() {
        assertNull(mapper.toDTO(null));
    }
}