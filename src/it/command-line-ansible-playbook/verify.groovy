String output = new File( basedir, "build.log" ).text;

assert output =~ "ansible-playbook -c local -i .*/my-hosts .*/playbook.yml"
assert output =~ "Working directory: /tmp"
