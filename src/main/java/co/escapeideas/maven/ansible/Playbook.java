package co.escapeideas.maven.ansible;

import java.io.IOException;
import java.util.List;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

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
    @Parameter( defaultValue = "ansible-playbook", required = true, property = "ansible.executable" )
    private String executable;

    /**
     * Additional variables as key=value or YAML/JSON
     */
    @Parameter( property = "ansible.extraVars" )
    private List<String> extraVars;

    /**
     * Only run plays and tasks whose tags do not match these values
     */
    @Parameter( property = "ansible.skipTags" )
    private String skipTags;

    /**
     * Check syntax only mode
     */
    @Parameter(property = "ansible.syntaxCheck")
    private boolean syntaxCheck;

    /**
     * Start the playbook at the task matching this name
     */
    @Parameter( property = "ansible.startAtTask" )
    private String startAtTask;

    /**
     * Only run plays and tasks tagged with these values
     */
    @Parameter( property = "ansible.tags" )
    private String tags;

    /**
     * The playbook to run, defaults to <b>playbook.yml</b>
     */
    @Parameter( defaultValue = "playbook.yml", required = true, property = "ansible.playbook" )
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
        command.addAll(createOption("--syntax-check", syntaxCheck));
    }

}
