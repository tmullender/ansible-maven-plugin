String output = new File(basedir, "build.log" ).text;

assert output =~ "ansible-pull -C branch -d directory -m setup"
assert output =~ "Working directory: /tmp"
