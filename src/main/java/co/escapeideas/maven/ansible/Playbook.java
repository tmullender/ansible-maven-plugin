package co.escapeideas.maven.ansible;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Goal that runs an Ansible playbook
 *
 */
@Mojo( name = "playbook", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST, requiresProject = false )
public class Playbook extends AbstractAnsibleMojo
{
    /**
     * The executable to use for this execution, defaults to <b>ansible-playbook</b>
     */
    @Parameter( defaultValue = "ansible-playbook", required = true )
    private String executable;

    /**
     * Additional variables as key=value or YAML/JSON
     */
    @Parameter
    private String extraVars;

    /**
     * Only run plays and tasks whose tags do not match these values
     */
    @Parameter
    private String skipTags;

    /**
     * Start the playbook at the task matching this name
     */
    @Parameter
    private String startAtTask;

    /**
     * Only run plays and tasks tagged with these values
     */
    @Parameter
    private String tags;

    /**
     * The playbook to run, defaults to <b>playbook.yml</b>
     */
    @Parameter( defaultValue = "playbook.yml", required = true )
    private String playbook;

    @Override
    protected String getArgument() throws IOException {
        return findClasspathFile(playbook);
    }

    @Override
    protected String getExecutable() {
        return executable;
    }

    @Override
    protected void addOptions(final List<String> command) {
        super.addOptions(command);
        command.addAll(createOption("-e", extraVars));
        command.addAll(createOption("--skip-tags", skipTags));
        command.addAll(createOption("--start-at-task", startAtTask));
        command.addAll(createOption("-t", tags));
    }

    /**
     * Checks whether the given file is an absolute path or a classpath file
     * @param path
     * @return
     * @throws IOException
     */
    private String findClasspathFile(final String path) throws IOException {
        if (path == null){
            return null;
        }
        final File file = new File(path);
        if (file.exists()){
            return file.getAbsolutePath();
        }
        return createTmpFile(path).getAbsolutePath();
    }

    /**
     * Copies a classpath resource to the tmp directory to allow it to be run by ansible
     * @param path
     * @return
     * @throws IOException if the path is not found
     */
    private File createTmpFile(final String path) throws IOException {
        getLog().debug("Creating temporary file for: " + path);
        final File output = new File(System.getProperty("java.io.tmpdir"), "ansible-maven-plugin." + System.nanoTime());
        final FileOutputStream outputStream = new FileOutputStream(output);
        final InputStream inputStream = getClass().getResourceAsStream("/" + path);
        if (inputStream == null){
            throw new FileNotFoundException("Unable to locate: " + path);
        }
        copy(inputStream, outputStream);
        return output;
    }

    /**
     * Copies the input stream to the output stream using a 4K buffer
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    private void copy(final InputStream inputStream, final FileOutputStream outputStream) throws IOException {
        final byte[] buffer = new byte[1024*4];
        int n;
        try {
            while (-1 != (n = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, n);
            }
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

}
