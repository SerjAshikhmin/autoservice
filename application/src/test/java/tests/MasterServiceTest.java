package tests;

import com.senla.courses.autoservice.dao.interfaces.IMasterDao;
import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterNotFoundException;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import com.senla.courses.autoservice.service.interfaces.IMasterService;
import config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class MasterServiceTest {

    @Autowired
    private IMasterService masterService;
    @Autowired
    private TestData testData;
    @Autowired
    private IMasterDao masterDao;

    @BeforeAll
    public static void startMasterServiceTests() {
        log.info("Starting master service tests");
    }

    @Test
    public void validateNewMasterAdding() {
        log.info("Validating new master adding");
        when(masterDao.addMaster(any(Master.class))).thenReturn(1);

        masterService.addMaster(new MasterDto(1, "Ivan", 1));

        verify(masterDao).addMaster(any(Master.class));
    }

    /*@ParameterizedTest
    @CsvSource({
        "Ivan, 1",
        "Arsen, 0"
    })
    public void validateMasterRemoving(String masterName, int expectedResult) {
        log.info("Validating master removing");
        when(masterDao.removeMaster(any(MasterDto.class))).thenReturn(1);
        when(masterDao.getAllMasters()).thenReturn(testData.getMastersList());

        masterService.removeMaster(masterName);

        verify(masterDao).removeMaster(any(Master.class));
    }

    @Test
    public void validateMasterUpdating() {
        log.info("Validating master updating");
        when(masterDao.updateMaster(any(Master.class))).thenReturn(1);
        MasterDto master = testData.getMastersList().get(0);
        master.setCategory(6);

        masterService.updateMaster(master);

        verify(masterDao).updateMaster(any(Master.class));
    }

    @Test
    public void validateGettingAllMasters() {
        log.info("Validating getting all masters");
        when(masterDao.getAllMasters()).thenReturn(testData.getMastersList());

        List<MasterDto> result = masterService.getAllDtoMasters();
        List<Master> expectedResult = testData.getMastersList();

        assertEquals(result, expectedResult);
        verify(masterDao, atLeastOnce()).getAllMasters();
    }

    @Test
    public void validateGettingAllMastersSorted() {
        log.info("Validating getting all masters sorted");
        List<Master> sortedByNameExpected = new ArrayList<>(testData.getMastersList());
        List<Master> sortedByBusyExpected = new ArrayList<>(testData.getMastersList());
        List<Master> noSortedExpected = testData.getMastersList();
        Collections.sort(sortedByNameExpected, Comparator.comparing(Master::getName));
        Collections.sort(sortedByBusyExpected, (master, t1) -> {
            if (master.isBusy() == t1.isBusy()) return 0;
            else
            if (master.isBusy()) return 1;
            else return -1;
        });

        List<Master> sortedByName = masterService.getAllMastersSorted("byName");
        List<Master> sortedByBusy = masterService.getAllMastersSorted("byBusy");
        List<Master> noSorted = masterService.getAllMastersSorted("wrongSortMethod");

        assertEquals(sortedByName, sortedByNameExpected);
        assertEquals(sortedByBusy, sortedByBusyExpected);
        assertEquals(noSorted, noSortedExpected);
        verify(masterDao, atLeastOnce()).getAllMasters();
    }

    @Test
    public void validateGettingAllFreeMasters() {
        log.info("Validating getting all free masters");
        List<Master> expectedResult = testData.getAllFreeMasters();

        List<Master> result = masterService.getAllFreeMasters();

        assertEquals(result, expectedResult);
        verify(masterDao, atLeastOnce()).getAllMasters();
    }

    @ParameterizedTest
    @CsvSource({
            "Evgeniy, 0",
            "Ivan, 2"
    })
    public void validateGettingCurrentOrder(String masterName, int masterId) throws MasterNotFoundException {
        log.info("Validating current order getting");
        when(masterDao.getCurrentOrder(testData.getMastersList().get(masterId))).thenReturn(testData.getMastersList().get(masterId).getOrder());
        Order expectedResult = testData.getOrdersList().get(masterId);

        Order result = masterService.getCurrentOrder(masterName);

        assertEquals(result, expectedResult);
        verify(masterDao, atLeastOnce()).getCurrentOrder(any(Master.class));
    }

    @Test
    public void validateGettingCurrentOrderWithUnknownMaster() throws MasterNotFoundException {
        log.info("Validating current order getting with MasterNotFoundException");

        Order result = masterService.getCurrentOrder("Arsen");

        assertEquals(result, null);
        verify(masterDao).getCurrentOrder(null);
    }

    @ParameterizedTest
    @CsvSource({
            "Evgeniy, 0",
            "Ivan, 2"
    })
    public void validateFindingMasterByName(String masterName, Integer masterId) {
        log.info("Validating finding master by name");
        Master expectedResult = testData.getMastersList().get(masterId);

        Master result = masterService.findMasterByName(masterName);

        assertEquals(result, expectedResult);
        verify(masterDao, atLeastOnce()).getAllMasters();
    }

    @Test
    public void validateFindingUnknownMasterByName() {
        log.info("Validating finding unknown master by name");

        Master result = masterService.findMasterByName("Arsen");

        assertEquals(result, null);
        verify(masterDao, atLeastOnce()).getMasterById(anyInt());
    }

    @Test
    public void validateFindingMasterById() {
        log.info("Validating finding master by id");
        when(masterDao.getMasterById(1)).thenReturn(testData.getMastersList().get(0));
        Master expectedResult = testData.getMastersList().get(0);

        Master result = masterService.findMasterById(1);

        assertEquals(result, expectedResult);
        verify(masterDao, atLeastOnce()).getMasterById(anyInt());
    }

    @Test
    public void validateFindingUnknownMasterById() {
        log.info("Validating finding unknown master by id");

        Master result = masterService.findMasterById(1);

        assertEquals(result, null);
        verify(masterDao, atLeastOnce()).getMasterById(anyInt());
    }*/

    @AfterAll
    public static void endMasterServiceTests() {
        log.info("Master service tests are completed");
    }

}
