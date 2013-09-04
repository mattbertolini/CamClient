package com.mattbertolini.camclient.net.support.urlconnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Immutable.
 * @author Matt Bertolini
 */
public final class ContentType {
    public static final ContentType APPLICATION_FORM_URLENCODED = ContentType.create("application/x-www-form-urlencoded");
    private static final String PATTERN = "([a-zA-Z-]+\\/[a-zA-Z0-9.+-]+)(?:; *charset=([a-zA-Z0-9-_]*))?";

    /**
     * Default charset as defined by the HTTP protocol.
     */
    private static final String DEFAULT_CHARSET = "ISO-8859-1";

    private final String type;
    private final String charset;

    private ContentType(final String type, final String charset) {
        this.type = type;
        this.charset = charset;
    }

    public static ContentType create(final String type) {
        return create(type, null);
    }

    public static ContentType create(final String type, final String charset) {
        return new ContentType(type, charset);
    }

    public static ContentType fromHeader(final CharSequence contentTypeHeader) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(contentTypeHeader);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Input is not a valid content type header string.");
        }
        String type = matcher.group(1);
        String charset = matcher.group(2);
        return ContentType.create(type, charset);
    }

    public String getType() {
        return this.type;
    }

    public String getCharset() {
        return this.charset;
    }

    public String getCharsetOrDefault() {
        return (this.charset == null) ? DEFAULT_CHARSET : this.charset;
    }

    public ContentType withCharset(final String charset) {
        return create(this.getType(), charset);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.type);
        if(this.charset != null && !this.charset.isEmpty()) {
            sb.append(";charset=").append(this.charset);
        }
        return sb.toString();
    }
}
