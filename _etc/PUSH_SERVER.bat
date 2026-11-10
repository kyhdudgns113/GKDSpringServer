@echo off
cd /d "%~dp0.."

git add --all
git commit -F "%~dp0_COMMIT_MSG_SERVER.md"
git push

cd /d "%~dp0..\.."
git add server
git commit -F "%~dp0_COMMIT_MSG_SERVER.md"
git push
