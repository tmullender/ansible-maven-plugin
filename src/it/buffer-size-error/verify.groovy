String output = new File(basedir, "build.log" ).text;

assert output =~ "IllegalArgumentException: Buffer size <= 0"