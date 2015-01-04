String output = new File( basedir, "build.log" ).text;

assert output =~ "ERROR: ansible-playbook -c ERROR -i [^ ]*/hosts [^ ]*/playbook.yml"
