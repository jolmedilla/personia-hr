package com.personia.hr.algorithm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FloydsTraverseAlgorithmTest {
    
    static final Map<String,String> SAMPLE_WITH_LOOP = 
            Map.of("Andrew","Sophie","Pete","Barbara","Barbara","Nick","Nick","Pete","Sophie","Juan");

    @InjectMocks
    private FloydsTraverseAlgorithm floydsTraverseAlgorithm;

    @ParameterizedTest
    @ValueSource(strings={"Pete","Barbara","Nick"})
    public void shouldDetecCycle(String entry) {
        assertThat(floydsTraverseAlgorithm.traverse(SAMPLE_WITH_LOOP,entry)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings={"Andrew","Sophie",})
    public void shouldNotDetectCycle(String entry) {
        assertThat(floydsTraverseAlgorithm.traverse(SAMPLE_WITH_LOOP,entry)).isFalse();
    }

}