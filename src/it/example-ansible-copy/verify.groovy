String output = new File( basedir, "target/output" ).text;

assert output =~ "ansible -c local -a src=a dest=b -m copy localhost"
