String output = new File( basedir, "build.log" ).text;

assert output =~ "ansible -c local -B 3 -a sleep 2 -m command -P 1 localhost"
