package ru.pogosian.infrastructure.repository.RepositoryImpl;

import ru.pogosian.business.excrptions.DomainValidationException;
import ru.pogosian.business.testDrive.TestDriveRequest;
import ru.pogosian.business.repositories.TestDriveRequestRepository;

import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDriveRequestRepositoryImpl implements TestDriveRequestRepository {
    private Map<UUID, TestDriveRequest> store =  new HashMap<UUID, TestDriveRequest>();
    @Override
    public void save(TestDriveRequest testDriveRequest) {
        if(testDriveRequest.isCarCapableForTestDrive() == false)
            throw new DomainValidationException("Car is not capable for test drive");
        store.put(testDriveRequest.getTestDriveId(), testDriveRequest);
    }

    @Override
    public TestDriveRequest findById(UUID id) {
        if(!store.containsKey(id))
            throw new DomainValidationException("Test drive request not found");
        TestDriveRequest TestDriveRequest = store.get(id);
        return TestDriveRequest;
    }

    @Override
    public List<TestDriveRequest> findAll() {
        return new ArrayList<TestDriveRequest>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        if(!store.containsKey(id))
            throw new DomainValidationException("Test drive request not found");
        store.remove(id);
    }
}