String output = new File( basedir, "build.log" ).text;

assert output =~ "ansible-playbook -c local -i [^ ]*/my-hosts -e a=b -e c=d [^ ]*/playbook.yml"
assert output =~ "Working directory: /tmp"
