package co.escapeideas.maven.ansible;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Goal to set up a remote copy of ansible on each managed node
 */
@Mojo( name = "pull", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST, requiresProject = false )
public class Pull extends AbstractAnsibleMojo {

    /**
     * The executable to use for this execution, defaults to <b>ansible-playbook</b>
     */
    @Parameter( defaultValue = "ansible-pull", required = true, property = "ansible.executable" )
    private String executable;

    /**
     * The branch, tag or commit to checkout
     */
    @Parameter( property = "ansible.checkout" )
    private String checkout;

    /**
     * The directory to checkout repository to
     */
    @Parameter( property = "ansible.directory" )
    private String directory;

    /**
     * Additional variables as key=value or YAML/JSON
     */
    @Parameter( property = "ansible.extraVars" )
    private String extraVars;

    /**
     * Run the playbook even if the repository could not be updated, defaults to false
     */
    @Parameter( property = "ansible.force", defaultValue = "false" )
    private boolean force;

    /**
     * Module name used to check out repository
     */
    @Parameter( property = "ansible.moduleName" )
    private String moduleName;

    /**
     * Only run the playbook if the repository has been updated, defaults to false
     */
    @Parameter( property = "ansible.onlyIfChanged", defaultValue = "false" )
    private boolean onlyIfChanged;

    /**
     * Purge checkout after playbook run, defaults to false
     */
    @Parameter( property = "ansible.purge", defaultValue = "false" )
    private boolean purge;

    /**
     * Sleep for a random interval upto this number of seconds
     */
    @Parameter( property = "ansible.sleep" )
    private Integer sleep;

    /**
     * URL of the playbook repository
     */
    @Parameter( property = "ansible.url" )
    private String url;

    /**
     * The playbook to run, defaults to <b>playbook.yml</b>
     */
    @Parameter( property = "ansible.playbook" )
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
        command.addAll(createOption("-C", checkout));
        command.addAll(createOption("-d", directory));
        command.addAll(createOption("-e", extraVars));
        command.addAll(createOption("-f", force));
        command.addAll(createOption("-i", getInventory()));
        command.addAll(createOption("-m", moduleName));
        command.addAll(createOption("-o", onlyIfChanged));
        command.addAll(createOption("--purge", purge));
        command.addAll(createOption("-s", sleep));
        command.addAll(createOption("-U", url));
        command.addAll(createOption("--vault-password-file", getVaultPasswordFile()));
    }

}
