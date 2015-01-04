String output = new File( basedir, "build.log" ).text;

assert output =~ "ansible-playbook -c local -e x=y -e a=b [^ ]*/playbook.yml"
