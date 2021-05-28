package com.personia.hr;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"toggles.use-persistence=false"})
@AutoConfigureMockMvc
public class GetSupervisorAndSupervisorsSupervisorsInMemoryTest extends InMemoryBaseTest implements GetSupervisorAndSupervisorsSupervisorsTest {
}
