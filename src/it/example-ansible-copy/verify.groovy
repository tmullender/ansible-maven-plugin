String output = new File( basedir, "build.log" ).text;

assert output =~ "ansible -c local -a src=a dest=b -m copy localhost"
