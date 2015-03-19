String output = new File( basedir, "build.log" ).text;

assert output =~ "\\[INFO] ansible -c local -m ping localhost"
