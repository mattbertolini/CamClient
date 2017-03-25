package com.mattbertolini.camclient.net.urlconnection.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Immutable.
 * @author Matt Bertolini
 */
public final class ContentType {
    public static final ContentType APPLICATION_FORM_URLENCODED = ContentType.create("application", "x-www-form-urlencoded");

    /**
     * Default charset as defined by the HTTP protocol.
     */
    private static final String DEFAULT_CHARSET = "ISO-8859-1";
    private static final Pattern PATTERN = Pattern.compile("([a-zA-Z\\-\\*]+)/([a-zA-Z0-9.+\\-\\*]+)(?:; *charset=([a-zA-Z0-9-_]*))?");

    private final String type;
    private final String subtype;
    private final String charset;

    private ContentType(final String type, final String subtype, final String charset) {
        this.type = type;
        this.subtype = subtype;
        this.charset = charset;
    }

    public static ContentType create(final String type, final String subtype) {
        return create(type, subtype, null);
    }

    public static ContentType create(final String type, final String subtype, final String charset) {
        return new ContentType(type, subtype, charset);
    }

    public static ContentType valueOf(final CharSequence contentTypeHeader) {
        if(contentTypeHeader == null) {
            throw new IllegalArgumentException("Input is null.");
        }
        Matcher matcher = PATTERN.matcher(contentTypeHeader);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Input is not a valid content type header string.");
        }
        String type = matcher.group(1);
        String subtype = matcher.group(2);
        String charset = matcher.group(3);
        return ContentType.create(type, subtype, charset);
    }

    public String getType() {
        return this.type;
    }

    public String getSubtype() {
        return this.subtype;
    }

    public String getCharset() {
        return this.charset;
    }

    public String getCharsetOrDefault() {
        return (this.charset == null) ? DEFAULT_CHARSET : this.charset;
    }

    public ContentType withCharset(final String charset) {
        return create(this.getType(), this.getSubtype(), charset);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.type);
        sb.append("/");
        sb.append(this.subtype);
        if(this.charset != null && !this.charset.isEmpty()) {
            sb.append(";charset=").append(this.charset);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentType that = (ContentType) o;

        if (charset != null ? !charset.equals(that.charset) : that.charset != null) return false;
        if (subtype != null ? !subtype.equals(that.subtype) : that.subtype != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (subtype != null ? subtype.hashCode() : 0);
        result = 31 * result + (charset != null ? charset.hashCode() : 0);
        return result;
    }
}
