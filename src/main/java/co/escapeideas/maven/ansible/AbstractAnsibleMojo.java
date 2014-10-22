package co.escapeideas.maven.ansible;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract mojo for common configuration parameters and Runtime processing
 *
 * Created by tmullender on 22/10/14.
 */
public abstract class AbstractAnsibleMojo extends AbstractMojo {
    /**
     * Connection type to use
     */
    @Parameter
    private String connection;

    /**
     * The number of parallel processes to use
     */
    @Parameter
    private Integer forks;

    /**
     * The inventory host file
     */
    @Parameter
    private File inventory;

    /**
     * Limit selected hosts to an additional pattern
     */
    @Parameter
    private String limit;

    /**
     * The path to the ansible module library
     */
    @Parameter
    private File modulePath;

    /**
     * Use this file to authenticate the connection
     */
    @Parameter
    private File privateKey;

    /**
     * Override the SSH timeout in seconds
     */
    @Parameter
    private Integer timeout;

    /**
     * Connect as this user
     */
    @Parameter
    private String remoteUser;

    /**
     * Vault password file
     */
    @Parameter
    private File vaultPasswordFile;

    /**
     * The directory in which to run, defaults to the projects basedir
     */
    @Parameter( defaultValue = "${basedir}", required = true)
    private File workingDirectory;

    /**
     * Constructs a command from the configured parameters and executes it, logging output at debug
     *
     * @throws MojoExecutionException
     */
    public void execute() throws MojoExecutionException {
        try {
            final List<String> command = new ArrayList<String>();
            command.add(getExecutable());
            addOptions(command);
            command.add(getArgument());
            getLog().info("Working directory: " + workingDirectory);
            getLog().info("Command: " + command);
            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.directory(workingDirectory);
            logProcess(builder.start());
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to run playbook", e);
        }
    }

    private void logProcess(final Process start) throws IOException {
        final BufferedReader output = new BufferedReader(new InputStreamReader(start.getInputStream()));
        String line;
        while((line = output.readLine()) != null){
            getLog().debug(line);
        }
    }

    /**
     * The argument to be used for the command
     * @return
     * @throws IOException
     */
    protected abstract String getArgument() throws IOException;

    /**
     * The executable for the command
     * @return
     */
    protected abstract String getExecutable();

    /**
     * Adds the configured options to the list of command strings
     * @param command
     */
    protected void addOptions(final List<String> command) {
        command.addAll(createOption("-c", connection));
        command.addAll(createOption("-f", forks));
        command.addAll(createOption("-i", inventory));
        command.addAll(createOption("-l", limit));
        command.addAll(createOption("-M", modulePath));
        command.addAll(createOption("--private-key", privateKey));
        command.addAll(createOption("-T", timeout));
        command.addAll(createOption("-u", remoteUser));
        command.addAll(createOption("--vault-password-file", vaultPasswordFile));
    }

    /**
     * Creates a list for the given option, an empty list if the option's value is null
     * @param option
     * @param value
     * @return
     */
    protected List<String> createOption(final String option, final Object value) {
        if (value == null){
            return new ArrayList<String>();
        }
        return Arrays.asList(option, String.valueOf(value));
    }
}
