String output = new File(basedir, "build.log" ).text;

assert output =~ "ansible-playbook -c local -i [^ ]*/my-hosts --syntax-check [^ ]*/playbook.yml"