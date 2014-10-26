String output = new File("/tmp/output" ).text;

assert output =~ "ansible -a 'src=a dest=b' -m copy localhost"
