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

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Class representing a MAC-48/EUI-48 MAC address.
 *
 * @author Matt Bertolini
 */
public final class MacAddress {
    public enum Delimiter {
        COLON(":", 2),
        HYPHEN("-", 2),
        NONE("", 1),
        PERIOD(".", 4);

        private final String delimiterCharacter;
        private final int spacing;

        private Delimiter(String delimiterCharacter, int spacing) {
            this.delimiterCharacter = delimiterCharacter;
            this.spacing = spacing;
        }

        public String getDelimiterCharacter() {
            return this.delimiterCharacter;
        }

        public int getSpacing() {
            return this.spacing;
        }
    }
    private static final Pattern EUI_48_PATTERN = Pattern.compile("^(?i)(?:[0-9a-f]{2}[-:]?){5}[0-9a-f]{2}|(?:[0-9a-f]{4}\\.?){2}[0-9a-f]{4}$");
    private static final int BYTE_ARRAY_LENGTH = 6;
    private static final long MIN_MAC_LONG_VALUE = 0L;
    private static final long MAX_MAC_LONG_VALUE = 281474976710655L;

    private final byte[] address;

    private MacAddress(final byte[] address) {
        this.address = Arrays.copyOf(address, BYTE_ARRAY_LENGTH);
    }

    public byte[] getBytes() {
        return Arrays.copyOf(this.address, BYTE_ARRAY_LENGTH);
    }

    public long getLong() {
        byte[] longBytes = new byte[8];
        System.arraycopy(this.address, 0, longBytes, 2, this.address.length);
        return ByteBuffer.wrap(longBytes).getLong();
    }

    @Override
    public String toString() {
        return this.toString(Delimiter.HYPHEN);
    }

    public String toString(Delimiter delimiter) {
        StringBuilder sb = new StringBuilder();
        final String macStr = this.macBytesToHex(this.address);
        int d = delimiter.getSpacing();
        for(int i = 0; i < macStr.length() - d; i = i + d) {
            sb.append(macStr.substring(i, i + d));
            sb.append(delimiter.getDelimiterCharacter());
        }
        sb.append(macStr.substring(macStr.length() - d, macStr.length()));
        return sb.toString().toUpperCase();
    }

    private String macBytesToHex(byte[] macBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : macBytes) {
            int v = b & 0xff;
            if(v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    public static MacAddress valueOf(CharSequence address) {
        if(address == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        if(!EUI_48_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("Input string is not a valid MAC address.");
        }
        final String addressStr = address.toString();
        final String cleanMac = addressStr.replaceAll("(?i)[^0-9a-f]", "");
        byte[] macBytes = new byte[BYTE_ARRAY_LENGTH];
        for (int i = 0; i < macBytes.length; i++) {
            macBytes[i] = (byte) Integer.parseInt(cleanMac.substring(2 * i, 2 * i + 2), 16);
        }
        return new MacAddress(macBytes);
    }

    public static MacAddress valueOf(long address) {
        if(address < MIN_MAC_LONG_VALUE) {
            throw new IllegalArgumentException("Long cannot be negative");
        }
        if(address > MAX_MAC_LONG_VALUE) {
            throw new IllegalArgumentException("Input long is larger than maximum valid MAC address");
        }
        byte[] longBytes = ByteBuffer.allocate(8).putLong(address).array();
        byte[] macBytes = Arrays.copyOfRange(longBytes, 2, longBytes.length);
        return new MacAddress(macBytes);
    }

    public static MacAddress valueOf(byte[] address) {
        if(address == null) {
            throw new IllegalArgumentException("MAC address bytes cannot be null.");
        }
        if(address.length != BYTE_ARRAY_LENGTH) {
            throw new IllegalArgumentException("MAC address byte array must be 6 bytes");
        }
        return new MacAddress(address);
    }

    public static boolean validate(String address) {
        if(address == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }
        return EUI_48_PATTERN.matcher(address).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MacAddress)) return false;

        MacAddress that = (MacAddress) o;

        if (!Arrays.equals(address, that.address)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(address);
    }
}
