package co.escapeideas.maven.ansible;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by tmullender on 30/10/14.
 */
public class NoopWriter extends Writer {
    @Override
    public void write(final char[] cbuf, final int off, final int len) throws IOException {

    }

    @Override
    public void write(final String str) throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
