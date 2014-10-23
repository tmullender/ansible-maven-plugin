String output = new File( basedir, "target/output" ).text;

assert output =~ "ansible-playbook -c local -i .*/hosts -e var=value .*/playbook.yml"
