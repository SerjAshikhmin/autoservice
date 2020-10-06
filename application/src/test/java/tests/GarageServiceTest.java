package tests;

import com.senla.courses.autoservice.dao.interfaces.IGarageDao;
import com.senla.courses.autoservice.dao.interfaces.IGaragePlaceDao;
import com.senla.courses.autoservice.dao.interfaces.IMasterDao;
import com.senla.courses.autoservice.model.Garage;
import com.senla.courses.autoservice.model.GaragePlace;
import com.senla.courses.autoservice.service.GarageService;
import com.senla.courses.autoservice.service.MasterService;
import config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class GarageServiceTest {

    @Autowired
    private GarageService garageService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private TestData testData;
    @Autowired
    private IGarageDao garageDao;
    @Autowired
    private IGaragePlaceDao garagePlaceDao;
    @Autowired
    private IMasterDao masterDao;

    @BeforeAll
    public static void startMasterServiceTests() {
        log.info("Starting garage service tests");
    }

    @Test
    public void validateNewGarageAdding() {
        log.info("Validating new garage adding");
        when(garageDao.addGarage(any(Garage.class))).thenReturn(1);

        int result = garageService.addGarage(new Garage(1, "Orel-Moskovskaya-22", new ArrayList<>()));

        assertEquals(result, 1);
        verify(garageDao).addGarage(any(Garage.class));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "5, 0"
    })
    public void validateGarageRemoving(int garageId, int expectedResult) {
        log.info("Validating garage removing");
        when(garageDao.removeGarage(any(Garage.class))).thenReturn(1);
        when(garageDao.getGarageById(1)).thenReturn(testData.getGarageList().get(0));

        int result = garageService.removeGarage(garageId);

        assertEquals(result, expectedResult);
        verify(garageDao).removeGarage(any(Garage.class));
    }

    @Test
    public void validateGettingAllGarages() {
        log.info("Validating getting all garages");
        when(garageDao.getAllGarages()).thenReturn(testData.getGarageList());

        List<Garage> result = garageService.getAllGarages();
        List<Garage> expectedResult = testData.getGarageList();

        assertEquals(result, expectedResult);
        verify(garageDao, atLeastOnce()).getAllGarages();
    }

    @Test
    public void validateNewGaragePlaceAdding() {
        log.info("Validating new garage place adding");
        when(garagePlaceDao.addGaragePlace(any(GaragePlace.class))).thenReturn(1);

        int result = garageService.addGaragePlace(new GaragePlace(1, garageService.findGarageById(1), "Car lift", 8));

        assertEquals(result, 1);
        verify(garagePlaceDao).addGaragePlace(any(GaragePlace.class));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 1",
            "1, 5, 0"
    })
    public void validateGaragePlaceRemoving(int garageId, int garagePlaceId, int expectedResult) {
        log.info("Validating garage place removing");
        when(garagePlaceDao.removeGaragePlace(any(GaragePlace.class))).thenReturn(1);
        when(garagePlaceDao.getGaragePlaceById(1, 1)).thenReturn(testData.getGarageList().get(0).getGaragePlaces().get(0));

        int result = garageService.removeGaragePlace(garageId, garagePlaceId);

        assertEquals(result, expectedResult);
        verify(garagePlaceDao).removeGaragePlace(any(GaragePlace.class));
    }

    @Test
    public void validateGettingAllFreePlaces() {
        log.info("Validating getting all free places");
        List<GaragePlace> expectedResult = testData.getAllFreePlaces();

        List<GaragePlace> result = garageService.getAllFreePlaces();

        assertEquals(result, expectedResult);
        verify(garageDao, atLeastOnce()).getAllGarages();
    }

    @Test
    public void validateGettingFreePlacesCountInFuture() {
        log.info("Validating of getting free places count in future");
        when(masterDao.getAllMasters()).thenReturn(testData.getMastersList());
        int expectedResult = Math.min(testData.getAllFreePlaces().size(), testData.getAllFreeMasters().size());

        int result = garageService.getFreePlacesCountInFuture();

        assertEquals(result, expectedResult);
        verify(garageDao, atLeastOnce()).getAllGarages();
        verify(masterDao, atLeastOnce()).getAllMasters();
    }

    @Test
    public void validateFindingGaragePlaceById() {
        log.info("Validating finding garage place by id");
        GaragePlace expectedResult = testData.getGarageList().get(0).getGaragePlaces().get(0);

        GaragePlace result = garageService.findGaragePlaceById(1, 1);

        assertEquals(result, expectedResult);
        verify(garagePlaceDao, atLeastOnce()).getGaragePlaceById(anyInt(), anyInt());
    }

    @Test
    public void validateFindingUnknownGaragePlaceById() {
        log.info("Validating finding unknown garage place by id");

        GaragePlace result = garageService.findGaragePlaceById(10, 1);

        assertEquals(result, null);
        verify(garagePlaceDao, atLeastOnce()).getGaragePlaceById(anyInt(), anyInt());
    }

    @Test
    public void validateFindingGarageById() {
        log.info("Validating finding garage by id");
        when(garageDao.getGarageById(1)).thenReturn(testData.getGarageList().get(0));
        Garage expectedResult = testData.getGarageList().get(0);

        Garage result = garageService.findGarageById(1);

        assertEquals(result, expectedResult);
        verify(garageDao, atLeastOnce()).getGarageById(anyInt());
    }

    @Test
    public void validateFindingUnknownGarageById() {
        log.info("Validating finding unknown garage by id");

        Garage result = garageService.findGarageById(10);

        assertEquals(result, null);
        verify(garageDao, atLeastOnce()).getGarageById(anyInt());
    }

    @AfterAll
    public static void endGarageServiceTests() {
        log.info("Garage service tests are completed");
    }

}
