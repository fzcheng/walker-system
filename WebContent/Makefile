YEELO_SDK:=yeelosdk:/usr/share/tomcat6/webapps/
EXCLUDE:=exclude.txt


default:
	@echo "sync to ipostest:\n\tmake ipostest" 
	@echo "sync to all:\n\tmake all" 

yeelosdk:
	rsync -av ../yeelosdk ${YEELO_SDK} --exclude-from=${EXCLUDE}

all: yeelosdk
