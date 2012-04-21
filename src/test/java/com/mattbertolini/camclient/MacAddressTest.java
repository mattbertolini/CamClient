/*
 * Copyright (c) 2012, Matthew Bertolini
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     * Neither the name of CamClient nor the names of its contributors may be
 *       used to endorse or promote products derived from this software without
 *       specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
