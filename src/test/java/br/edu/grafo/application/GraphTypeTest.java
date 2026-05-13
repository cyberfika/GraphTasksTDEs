package br.edu.grafo.application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraphTypeTest {

    @Test
    void none_hasCorrectMetadata() {
        assertEquals("No graph", GraphType.NONE.getDefaultDisplayName());
        assertEquals("units", GraphType.NONE.getWeightUnit());
        assertFalse(GraphType.NONE.isUserSaved());
    }

    @Test
    void manual_hasCorrectMetadata() {
        assertEquals("Manual graph", GraphType.MANUAL.getDefaultDisplayName());
        assertEquals("units", GraphType.MANUAL.getWeightUnit());
        assertFalse(GraphType.MANUAL.isUserSaved());
    }

    @Test
    void curitibaWalk_hasCorrectMetadata() {
        assertEquals("Curitiba walk graph", GraphType.CURITIBA_WALK.getDefaultDisplayName());
        assertEquals("km", GraphType.CURITIBA_WALK.getWeightUnit());
        assertFalse(GraphType.CURITIBA_WALK.isUserSaved());
    }

    @Test
    void solarSystem_hasCorrectMetadata() {
        assertEquals("Solar system graph", GraphType.SOLAR_SYSTEM.getDefaultDisplayName());
        assertEquals("AU", GraphType.SOLAR_SYSTEM.getWeightUnit());
        assertFalse(GraphType.SOLAR_SYSTEM.isUserSaved());
    }

    @Test
    void solarSystemHyperspace_hasCorrectMetadata() {
        assertEquals("Solar system hyperspace graph", GraphType.SOLAR_SYSTEM_HYPERSPACE.getDefaultDisplayName());
        assertEquals("route cost", GraphType.SOLAR_SYSTEM_HYPERSPACE.getWeightUnit());
        assertFalse(GraphType.SOLAR_SYSTEM_HYPERSPACE.isUserSaved());
    }

    @Test
    void userSaved_hasCorrectMetadata() {
        assertEquals("", GraphType.USER_SAVED.getDefaultDisplayName());
        assertEquals("units", GraphType.USER_SAVED.getWeightUnit());
        assertTrue(GraphType.USER_SAVED.isUserSaved());
    }

    @Test
    void enum_hasExactlySixConstants() {
        GraphType[] values = GraphType.values();
        assertEquals(6, values.length);
    }
}
