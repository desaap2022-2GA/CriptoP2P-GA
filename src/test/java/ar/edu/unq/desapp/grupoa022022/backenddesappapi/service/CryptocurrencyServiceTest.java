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

    DataSet dataSet = new DataSet();

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    public Cryptocurrency mockCrypto = Mockito.mock(Cryptocurrency.class);
    public CryptocurrencyRegister mockCriptoReg1 = Mockito.mock(CryptocurrencyRegister.class);
    public CryptocurrencyRegister mockCriptoReg2 = Mockito.mock(CryptocurrencyRegister.class);


    @BeforeEach
    public void init() throws ResourceNotFound {
        Mockito.when(mockCrypto.getId()).thenReturn(1);
        Mockito.when(mockCrypto.getName()).thenReturn("DAI");
        Mockito.when(mockCriptoReg1.getPrice()).thenReturn(320.38d);
        Mockito.when(mockCriptoReg1.getName()).thenReturn("DAI");
        Mockito.when(mockCriptoReg2.getName()).thenReturn("BITCOIN");
        Mockito.when(mockCriptoReg2.getPrice()).thenReturn(5840798.98d);
    }

    //no funca
    @DisplayName("JUnit test create method in CryptocurrencyService")
    @Test
    public void createCryptocurrencyTest(){
        System.out.println("--------------aca1");
        CryptocurrencyRegister cryptocurrencyRegister= new CryptocurrencyRegister(mockCriptoReg1.getName(),
                mockCriptoReg1.getPrice());
        System.out.println("----------------aca2");
        System.out.println("name: " + cryptocurrencyRegister.getName());
        System.out.println("name: " + cryptocurrencyRegister.getPrice());
        System.out.println("-----------------------------------------------");
        Cryptocurrency cryptoReg = cryptocurrencyService.create(cryptocurrencyRegister);
        System.out.println("----------------aca3");
        //System.out.println("nameCreate: " + cryptoReg.getName());

        assertEquals(cryptoReg.getName(), "DAI");
    }

    //no funca
    @DisplayName("Junit test deleteAll method in CryptocurrencyService")
    @Test
    public void deleteAllCryptoTest(){
        cryptocurrencyService.deleteAll();

        assertEquals(cryptocurrencyService.getAll().size(), 0);
    }



    @DisplayName("Junit test getAll method in CryptocurrencyService")
    @Test
    public void getAllCryptoTest(){
        assertEquals(cryptocurrencyService.getAll().size(),16);
    }

    //delete(id) no funca
    @DisplayName("Junit test delete method in CryptocurrencyService")
    @Test
    public void deleteCryptoTest(){
        cryptocurrencyService.delete(1);

        assertEquals(cryptocurrencyService.getAll().size(), 15);
    }

    @DisplayName("Junit test findById method in CryptocurrencyService")
    @Test
    public void findByIdTest() throws ResourceNotFound {
        String cryptoName = mockCrypto.getName();

        assertEquals(cryptoName, cryptocurrencyService.findById(1).getName());
    }






}
