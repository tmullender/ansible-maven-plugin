String output = new File( basedir, "build.log" ).text;

assert output =~ "ERROR: ansible -c ERROR -m ping localhost"
assert output =~ "Non-zero exit status returned"
