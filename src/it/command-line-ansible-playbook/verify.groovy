String output = new File( "/tmp/output" ).text;

assert output =~ "ansible-playbook -c local -i .*/my-hosts .*/playbook.yml"
