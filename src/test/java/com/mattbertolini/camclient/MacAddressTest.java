package com.mattbertolini.camclient;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matt Bertolini
 */
public class MacAddressTest {
    @Test
    public void testValueOfStringWithColons() {
        byte[] expected = { 1, 35, 69, 103, -119, -85 };
        MacAddress address = MacAddress.valueOf("01:23:45:67:89:AB");
        Assert.assertArrayEquals(expected, address.getBytes());
    }

    @Test
    public void testValueOfStringWithHyphens() {
        byte[] expected = { 1, 35, 69, 103, -119, -85 };
        MacAddress address = MacAddress.valueOf("01-23-45-67-89-AB");
        Assert.assertArrayEquals(expected, address.getBytes());
    }

    @Test
    public void testValueOfStringWithPeriods() {
        byte[] expected = { 1, 35, 69, 103, -119, -85 };
        MacAddress address = MacAddress.valueOf("0123.4567.89AB");
        Assert.assertArrayEquals(expected, address.getBytes());
    }

    @Test
    public void testValueOfStringWithNoFormatting() {
        byte[] expected = { 1, 35, 69, 103, -119, -85 };
        MacAddress address = MacAddress.valueOf("0123456789AB");
        Assert.assertArrayEquals(expected, address.getBytes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfStringNullArgument() {
        MacAddress.valueOf((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfStringInvalidMacString() {
        MacAddress.valueOf("Not a MAC");
    }

    @Test
    public void testValueOfLong() {
        byte[] expectedBytes = { 1, 111, 35, 94, 69, 76 };
        long expected = 1576846378316L;
        MacAddress macAddress = MacAddress.valueOf(expected);
        long macLong = macAddress.getLong();
        Assert.assertEquals(expected, macLong);
        Assert.assertArrayEquals(expectedBytes, macAddress.getBytes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfLongNegativeValue() {
        MacAddress.valueOf(-42L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfLongAboveMaxValue() {
        MacAddress.valueOf(320255973501901L);
    }

    @Test
    public void testValueOfByteArray() {
        byte[] expected = { -85, 105, 47, 52, 108, 18 };
        MacAddress address = MacAddress.valueOf(expected);
        Assert.assertArrayEquals(expected, address.getBytes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfByteArrayNullArgument() {
        MacAddress.valueOf((byte[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfByteArrayTooSmall() {
        MacAddress.valueOf(new byte[] { -85, 105, 47, 52});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfByteArrayTooLarge() {
        MacAddress.valueOf(new byte[] { -85, 105, 47, 52, 108, 18, 0});
    }

    @Test
    public void testValidateTrue() {
        Assert.assertTrue(MacAddress.validate("001122334455"));
    }

    @Test
    public void testValidateFalse() {
        Assert.assertFalse(MacAddress.validate("abc123 not a mac"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateNullArgument() {
        MacAddress.validate(null);
    }

    @Test
    public void testToStringNoArgument() {
        MacAddress address = MacAddress.valueOf("0123456789ab");
        Assert.assertEquals("01-23-45-67-89-AB", address.toString());
    }

    @Test
    public void testToStringWithColons() {
        MacAddress address = MacAddress.valueOf("0123456789ab");
        Assert.assertEquals("01:23:45:67:89:AB", address.toString(MacAddress.Delimiter.COLON));
    }

    @Test
    public void testToStringWithHyphens() {
        MacAddress address = MacAddress.valueOf("0123456789ab");
        Assert.assertEquals("01-23-45-67-89-AB", address.toString(MacAddress.Delimiter.HYPHEN));
    }

    @Test
    public void testToStringWithPeriods() {
        MacAddress address = MacAddress.valueOf("0123456789ab");
        Assert.assertEquals("0123.4567.89AB", address.toString(MacAddress.Delimiter.PERIOD));
    }

    @Test
    public void testToStringWithNoFormatting() {
        MacAddress address = MacAddress.valueOf("0123456789ab");
        Assert.assertEquals("0123456789AB", address.toString(MacAddress.Delimiter.NONE));
    }

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(MacAddress.class).suppress(Warning.NULL_FIELDS).verify();
    }
}
