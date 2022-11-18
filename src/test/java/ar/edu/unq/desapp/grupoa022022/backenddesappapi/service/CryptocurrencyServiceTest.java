package ar.edu.unq.desapp.grupoa022022.backenddesappapi.service;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.DataSet;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.dto.CryptocurrencyRegister;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.exceptions.ResourceNotFound;
import ar.edu.unq.desapp.grupoa022022.backenddesappapi.service.serviceimpl.CryptocurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CryptocurrencyServiceTest {

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    public Cryptocurrency mockCrypto = Mockito.mock(Cryptocurrency.class);
    public CryptocurrencyRegister mockCryptoReg1 = Mockito.mock(CryptocurrencyRegister.class);
    public CryptocurrencyRegister mockCryptoReg2 = Mockito.mock(CryptocurrencyRegister.class);


    @BeforeEach
    public void init() throws ResourceNotFound {
        Mockito.when(mockCrypto.getId()).thenReturn(1);
        Mockito.when(mockCrypto.getName()).thenReturn("DAI");
        Mockito.when(mockCryptoReg1.getPrice()).thenReturn(320.38d);
        Mockito.when(mockCryptoReg1.getName()).thenReturn("DAI");
        Mockito.when(mockCryptoReg2.getName()).thenReturn("BITCOIN");
        Mockito.when(mockCryptoReg2.getPrice()).thenReturn(5840798.98d);
    }
/*
    //no funca
    @DisplayName("JUnit test create method in CryptocurrencyService")
    @Test
    void createCryptocurrencyTest(){
        CryptocurrencyRegister cryptocurrencyRegister= new CryptocurrencyRegister(mockCryptoReg1.getName(),
                mockCryptoReg1.getPrice());
        System.out.println("----------------aca2");
        System.out.println("name: " + cryptocurrencyRegister.getName());
        System.out.println("name: " + cryptocurrencyRegister.getPrice());
        System.out.println("-----------------------------------------------");
        Cryptocurrency crypto = cryptocurrencyService.create(cryptocurrencyRegister);
        System.out.println("----------------aca3");

        assertEquals(crypto.getName(), "DAI");
    }

    //no funca
    @DisplayName("Junit test deleteAll method in CryptocurrencyService")
    @Test
    void deleteAllCryptoTest(){
        cryptocurrencyService.deleteAll();

        assertEquals(cryptocurrencyService.getAll().size(), 0);
    }

*/

    @DisplayName("Junit test getAll method in CryptocurrencyService")
    @Test
    void getAllCryptoTest(){
        assertEquals(cryptocurrencyService.getAll().size(),16);
    }

    /*
    //delete(id) no funca
    @DisplayName("Junit test delete method in CryptocurrencyService")
    @Test
    void deleteCryptoTest(){
        cryptocurrencyService.delete(1);

        assertEquals(cryptocurrencyService.getAll().size(), 15);
    }
*/
    @DisplayName("Junit test findById method in CryptocurrencyService")
    @Test
    void findByIdTest() throws ResourceNotFound {
        String cryptoName = mockCrypto.getName();

        assertEquals(cryptoName, cryptocurrencyService.findById(1).getName());
    }






}
