String output = new File( basedir, "target/output" ).text;

assert output =~ "ansible -c local -m ping localhost"
