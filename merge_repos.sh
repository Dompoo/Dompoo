# 작업 트리가 깨끗한지 확인
git status

# 필요하다면 변경 사항 커밋 또는 스태시
git add .
git commit -m "Prepare for adding subtrees"

# 각 레포지토리를 서브트리로 추가 (브랜치 이름이 main인 경우)
for repo in https://github.com/Dompoo/SpringBoot_Core_Boot.git https://github.com/Dompoo/SpringBoot_Core_Embed.git https://github.com/Dompoo/SpringBoot_Core_Server.git https://github.com/Dompoo/Spring_Core_AOP.git https://github.com/Dompoo/Spring_Core_Proxy.git https://github.com/Dompoo/Spring_Core_Callback.git https://github.com/Dompoo/Spring_DB2_Transaction.git https://github.com/Dompoo/Spring_DB2_DataTech.git; do
    repo_name=$(basename $repo .git)
    git remote add -f $repo_name $repo
    # master 브랜치 대신 main 브랜치를 사용하는 경우 아래 명령어를 수정하세요.
    git subtree add --prefix=$repo_name $repo_name master
done