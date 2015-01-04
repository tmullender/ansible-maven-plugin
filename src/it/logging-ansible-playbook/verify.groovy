String output = new File( basedir, "build.log" ).text;
String log = new File( basedir, "target/stdout.log" ).text;

assert output =~ "ansible-playbook -c local [^ ]*/playbook.yml"
assert log =~ "ansible-playbook -c local [^ ]*/playbook.yml"
