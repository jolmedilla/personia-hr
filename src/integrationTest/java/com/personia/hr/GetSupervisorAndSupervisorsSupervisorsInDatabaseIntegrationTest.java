package com.personia.hr;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"toggles.use-persistence=true"})
@AutoConfigureMockMvc
public class GetSupervisorAndSupervisorsSupervisorsInDatabaseIntegrationTest extends InDatabaseBaseIntegrationTest implements GetSupervisorAndSupervisorsSupervisorsIntegrationTest {
}
