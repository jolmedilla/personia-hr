package com.personia.hr.model;


import com.github.dozermapper.core.Mapper;
import com.personia.hr.repository.EmployeeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HierarchyPersistentImplTest implements HierarchyTest<HierarchyPersistentImpl> {

    @InjectMocks
    private HierarchyPersistentImpl hierarchyPersistent;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Mapper mapper;

    @Mock
    List<Employee> employeesWithoutSupervisor;

    @Override
    public HierarchyPersistentImpl getHierarchy() {
        return hierarchyPersistent;
    }

    @Override
    public void prepareShouldThrowExceptionWhenHierarchyHasMultipleRoots() {
        when(employeeRepository.findOptionalByNameIs(any(String.class))).thenReturn(Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());
        when(employeeRepository.findDistinctBySupervisorIsNull()).thenReturn(employeesWithoutSupervisor);
        when(employeesWithoutSupervisor.size()).thenReturn(2);
    }
}