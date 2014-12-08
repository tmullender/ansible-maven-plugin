String output = new File( basedir, "build.log" ).text;
String log = new File( basedir, "target/log/stdout.log" ).text;

assert output =~ "ansible -c local -m ping localhost"
assert log =~ "ansible -c local -m ping localhost"
