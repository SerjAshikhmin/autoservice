package com.senla.courses.autoservice.service.interfaces;

import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterAddingException;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterModifyingException;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterNotFoundException;
import com.senla.courses.autoservice.model.domain.Master;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IMasterService {

    void addMaster(MasterDto master) throws MasterAddingException;
    void removeMaster(String name) throws MasterModifyingException;
    void updateMaster(MasterDto master) throws MasterModifyingException;
    List<MasterDto> getAllDtoMasters() throws MasterNotFoundException;
    List<MasterDto> getAllMastersSorted(String sortBy) throws MasterNotFoundException;
    List<MasterDto> getAllFreeMasters() throws MasterNotFoundException;
    MasterDto findMasterByName(String name) throws MasterNotFoundException;
    MasterDto findMasterById(int id) throws MasterNotFoundException;
    void importMaster(String fileName);
    boolean exportMaster(int id, String fileName);
    List<String> toList(Master master);
    void saveState() throws MasterNotFoundException;
    void loadState();
}
