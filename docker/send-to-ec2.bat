@echo off
set key_path=C:\Users\sbl\programming\..workspace\sources\genti\genti-keypair.pem
set source_path=C:\Users\sbl\programming\..workspace\sources\genti\docker\deploy
set destination_path=ubuntu@ec2-3-34-99-42.ap-northeast-2.compute.amazonaws.com:/home/ubuntu/workspace

scp -i %key_path% -r %source_path% %destination_path%
