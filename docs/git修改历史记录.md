1. 删除已经提交文件的记录
    ```text
    git filter-branch -f --prune-empty --index-filter "git rm --cached --ignore-unmatch -fr ./node_modules" -- --all
    ```
2. 修改提交的所有用户名
    ```text
    git filter-branch -f --env-filter "GIT_AUTHOR_NAME=yichengjie" -- --all
    ```
3. 修改提交的所有邮箱
    ```text
    git filter-branch -f --env-filter "GIT_AUTHOR_EMAIL=626659321@qq.com" -- --all
    ```
4. 修改最后一次提交的邮箱
5. https://www.cnblogs.com/fwindpeak/p/7127689.html


