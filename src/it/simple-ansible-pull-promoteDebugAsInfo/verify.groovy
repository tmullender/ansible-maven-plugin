String output = new File( basedir, "build.log" ).text;

assert output =~ "\\[INFO] ansible-pull -C branch -d directory -e src=a dest=b -f -i .*/hosts -m module -o --purge -s 10 -U git://github.com --vault-password-file .*/vault .*/playbook.yml"
