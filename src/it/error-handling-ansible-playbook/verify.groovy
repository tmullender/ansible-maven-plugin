String output = new File( basedir, "build.log" ).text;

assert output =~ "ERROR: ansible-playbook -c ERROR .*/playbook.yml"
