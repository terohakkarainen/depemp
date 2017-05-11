package fi.thakki.depemp.controller;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import fi.thakki.depemp.dao.GenericDao;

@Profile("errorControllerTest")
@Configuration
public class ErrorControllerTestConfiguration {

    @Bean
    @Primary
    public GenericDao genericDao() {
        return Mockito.mock(GenericDao.class);
    }
}
