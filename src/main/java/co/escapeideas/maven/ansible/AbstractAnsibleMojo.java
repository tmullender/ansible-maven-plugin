package co.escapeideas.maven.ansible;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
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

    private static final String NEW_LINE_SEPARATOR = System.getProperty("line.separator");;
    private static final String STDERR_LOG = "stderr.log";
    private static final String STDOUT_LOG = "stdout.log";

    /**
     * Connection type to use
     */
    @Parameter( property = "ansible.connection" )
    private String connection;

    /**
     * The number of parallel processes to use
     */
    @Parameter( property = "ansible.forks" )
    private Integer forks;

    /**
     * The inventory host file
     */
    @Parameter( property = "ansible.inventory" )
    private File inventory;

    /**
     * Limit selected hosts to an additional pattern
     */
    @Parameter( property = "ansible.limit" )
    private String limit;

    /**
     * The path to the ansible module library
     */
    @Parameter( property = "ansible.modulePath" )
    private File modulePath;

    /**
     * Use this file to authenticate the connection
     */
    @Parameter( property = "ansible.privateKey" )
    private File privateKey;

    /**
     * Override the SSH timeout in seconds
     */
    @Parameter( property = "ansible.timeout" )
    private Integer timeout;

    /**
     * Connect as this user
     */
    @Parameter( property = "ansible.remoteUser" )
    private String remoteUser;

    /**
     * Vault password file
     */
    @Parameter( property = "ansible.vaultPasswordFile" )
    private File vaultPasswordFile;

    /**
     * The directory in which to run, defaults to the projects build directory
     */
    @Parameter( defaultValue = "${project.build.directory}", required = true, property = "ansible.workingDirectory" )
    private File workingDirectory;

    /**
     * If true, the build will fail when the ansible command returns an error(a non zero exit status),
     * defaults to false
     * @since 1.1.0
     */
    @Parameter( defaultValue = "false", property = "ansible.failOnAnsibleError" )
    private boolean failOnAnsibleError;

    /**
     * If present the plugin will log the output of the execution to files in this directory
     * @since 1.1.0
     */
    @Parameter( property = "ansible.logDirectory" )
    private File logDirectory;

    /**
     * Constructs a command from the configured parameters and executes it, logging output at debug
     *
     * @throws MojoExecutionException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            final List<String> command = createCommand();
            checkWorkingDirectory();
            final int status = execute(command);
            if (failOnAnsibleError && status != 0){
                throw new MojoFailureException("Non-zero exit status returned");
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to run playbook", e);
        } catch (InterruptedException e) {
            throw new MojoExecutionException("Run interrupted", e);
        }
    }

    private List<String> createCommand() throws IOException {
        final List<String> command = new ArrayList<String>();
        command.add(getExecutable());
        addOptions(command);
        command.add(getArgument());
        getLog().info("Command: " + command);
        return command;
    }

    private void checkWorkingDirectory() {
        if (!workingDirectory.exists()){
            workingDirectory = new File(System.getProperty("java.io.tmpdir"));
            getLog().info("Updated working directory: " + workingDirectory);
        } else {
            getLog().info("Working directory: " + workingDirectory);
        }
    }

    private int execute(final List<String> command) throws IOException, InterruptedException {
        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(workingDirectory);
        final Process process = builder.start();
        process.waitFor();
        logStream(process.getInputStream(), false);
        logStream(process.getErrorStream(), true);
        return process.exitValue();
    }

    private void logStream(final InputStream inputStream, final boolean error) throws IOException {
        final BufferedReader output = new BufferedReader(new InputStreamReader(inputStream));
        Writer fileWriter = null;
        try {
            fileWriter = createFileWriter(error);
            logStream(output, fileWriter, error);
        } finally {
            fileWriter.close();
        }
    }

    private void logStream(final BufferedReader input, final Writer output, final boolean error) throws IOException {
        String line;
        while((line = input.readLine()) != null){
            if (error) {
                getLog().warn(line);
            } else {
                getLog().debug(line);
            }
            output.write(line);
            output.write(NEW_LINE_SEPARATOR);
        }
    }

    private Writer createFileWriter(final boolean error) throws IOException {
        if (logDirectory == null){
            return new NoopWriter();
        }
        final File output = new File(logDirectory, error ? STDERR_LOG : STDOUT_LOG);
        return new FileWriter(output, true);
    }

    /**
     * The argument to be used for the command.
     * For <b>ansible</b> this is the hosts
     * For <b>ansible-playbook</b> this is the playbook
     * @return the argument to pass
     * @throws IOException
     */
    protected abstract String getArgument() throws IOException;

    /**
     * The executable for the command
     * @return the name of the command to run
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
     * @return a list of the strings for the given option, empty if the option should not be used
     */
    protected List<String> createOption(final String option, final Object value) {
        if (value == null){
            return new ArrayList<String>();
        }
        return Arrays.asList(option, String.valueOf(value));
    }
}
