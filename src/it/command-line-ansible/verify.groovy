String output = new File( basedir, "target/output" ).text;

assert output =~ "ansible -a 'src=a dest=b' -m copy localhost"
