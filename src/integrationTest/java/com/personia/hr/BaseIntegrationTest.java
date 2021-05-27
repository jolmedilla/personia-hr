package com.personia.hr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class BaseIntegrationTest implements PersoniaHrBaseIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @Override
    public MockMvc getMockMvc() {
        return mockMvc;
    }
}
