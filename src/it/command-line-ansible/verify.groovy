String output = new File("/tmp/output" ).text;

assert output =~ "ansible -c local -m setup localhost"
