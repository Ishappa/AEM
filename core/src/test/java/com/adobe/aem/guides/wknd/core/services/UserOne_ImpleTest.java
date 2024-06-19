package com.adobe.aem.guides.wknd.core.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class UserOne_ImpleTest {

    private UserOne_Imple userOneImple;

    @BeforeEach
    void setUp() {
        // Mock BundleContext and ServiceReference
        BundleContext bundleContext = mock(BundleContext.class);
        ServiceReference<UserOne_Imple.User> serviceReference = mock(ServiceReference.class);
        UserOne_Imple.User config = getMockConfig();

        // Mock behavior for BundleContext and ServiceReference
        when(bundleContext.getServiceReference(UserOne_Imple.User.class)).thenReturn(serviceReference);
        when(bundleContext.getService(serviceReference)).thenReturn(config);

        // Create instance of UserOne_Imple and activate
        userOneImple = new UserOne_Imple();
        userOneImple.activate(config);
    }

    @Test
    void testActivate() {
        assertEquals("Ish", userOneImple.getName());
        assertEquals(30, userOneImple.getAge());
        assertEquals("Bangalore", userOneImple.getLocation());
    }

    private UserOne_Imple.User getMockConfig() {
        UserOne_Imple.User config = mock(UserOne_Imple.User.class);
        when(config.name()).thenReturn("Ish");
        when(config.age()).thenReturn(30);
        when(config.location()).thenReturn("Bangalore");
        return config;
    }
}
