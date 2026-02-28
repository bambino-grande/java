package ru.pogosian.infrastructure.repository;

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
    public void save(TestDriveRequest TestDriveRequest) {
        store.put(TestDriveRequest.getTestDriveId(), TestDriveRequest);
    }

    @Override
    public TestDriveRequest findById(UUID id) {
        TestDriveRequest TestDriveRequest = store.get(id);
        return TestDriveRequest;
    }

    @Override
    public List<TestDriveRequest> findAll() {
        return new ArrayList<TestDriveRequest>(store.values());
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }
}