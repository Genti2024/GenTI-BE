
# COMMON

<br>

- ### Settings for docker

port, volume mount 등의 설정은 .env파일에서 변수를 관리한다.
.env 파일에는 container specific한 내용이 담기면 안된다.

예를 들어 아래와 같이 .env 파일을 작성하고,
``` text
# DB
DB_LOCAL_DIRECTORY=C:/Users/sbl/programming/..workspace/oracle/oracle-19c/oradata
DB_IMAGE_DIRECTORY=/opt/oracle/oradata
DB_BINDING_PORT=1521
DB_CONTAINER_PORT=1521
```
<br>
docker-compose.yml 파일을 아래와 같이 작성하면 된다.

```groovy
services:
  mycontainer:
    ports:
      - ${DB_BINDING_PORT}:${DB_CONTAINER_PORT}
    volumes:
      - ${DB_LOCAL_DIRECTORY}:${DB_IMAGE_DIRECTORY}
```
<br> <br> <br>

- ### Settings for container specific

각 Container에 Specific하게 적용되는 부분들은 {container_name}.env 과 같은 형식으로 env파일을 따로 생성하여 관리하고, docker-compose.yml 파일에서는 아래와 같이 관리한다.
``` groovy
service:
  mycontainer:
    env_file:
      - oracle.env
```


# ORACLE

일반적으로 docker run doctorkirk/oracle-19c 과같이 아무 설정도 없이 실행하면
database mount, SID 설정 등에 문제가 생긴다.

### 1. env_file for oracle

docker에서 외부적으로 SID를 지정해도 잘 듣지 않음
따라서 env_file에는 아래의 oracle 환경설정과 크게 상관없는 내용을 포함한다.
아래의 설정값들은 oracle startup scripts에 활용할 예정

- ORACLE_SERVER
- ORACLE_DB
- ORACLE_USER
- ORACLE_PASSWORD
- ORACLE_CHARACTERSET

### 2. create_user.sh startup script

```shell
ORACLE_SID=ORCLCDB; export ORACLE_SID;
./setPassword.sh password;
echo "ALTER SESSION SET \"_ORACLE_SCRIPT\"=true;
CREATE USER $ORACLE_USER IDENTIFIED BY \"$ORACLE_PASSWORD\";
GRANT ALL PRIVILEGES TO $ORACLE_USER;
exit
" | sqlplus SYS/password@ORCLCDB AS SYSDBA
```

1. ORACLE_SID를 export
2. _ORACLE_SCRIPT = true 로 사용자명 앞에 C##을 붙이지 않아도 CREATE USER 가능
3. ORACLE_USER에 모든 권한 부여