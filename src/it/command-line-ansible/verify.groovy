String output = new File(basedir, "build.log" ).text;

assert output =~ "ansible -c local -m setup localhost"
assert output =~ "Working directory: /tmp"
