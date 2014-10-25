package co.escapeideas.maven.ansible;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.util.List;

/**
 * Goal that runs an Ansible command
 *
 * @author tmullender on 22/10/14.
 */
@Mojo( name = "ansible", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST, requiresProject = false)
public class Ansible extends AbstractAnsibleMojo {

    /**
     * The executable to use for this execution, defaults to <b>ansible</b>
     */
    @Parameter( defaultValue = "ansible", required = true, property = "ansible.executable" )
    private String executable;

    /**
     * Run asynchronously, failing after this number of seconds
     */
    @Parameter( property = "ansible.background")
    private Integer background;

    /**
     * Pattern for matching hosts to run the module against, defaults to <b>localhost</b>
     */
    @Parameter( defaultValue = "localhost", required = true, property = "ansible.hosts" )
    private String hosts;

    /**
     * Module arguments
     */
    @Parameter( property = "ansible.moduleArgs" )
    private String moduleArgs;

    /**
     * Module name to execute, defaults to <b>ping</b>
     */
    @Parameter( defaultValue = "ping", required = true, property = "ansible.moduleName" )
    private String moduleName;

    /**
     * The poll interval if using <i>background</i>
     */
    @Parameter( property = "ansible.pollInterval" )
    private Integer pollInterval;

    @Override
    protected String getArgument() throws IOException {
        return hosts;
    }

    @Override
    protected String getExecutable() {
        return executable;
    }

    @Override
    protected void addOptions(final List<String> command) {
        super.addOptions(command);
        command.addAll(createOption("-B", background));
        command.addAll(createOption("-a", moduleArgs));
        command.addAll(createOption("-m", moduleName));
        command.addAll(createOption("-P", pollInterval));
    }
}
