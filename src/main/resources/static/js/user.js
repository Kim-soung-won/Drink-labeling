const createButton = document.getElementById('create-btn');

if (createButton) {
    // 등록 버튼을 클릭하면 /api/articles로 요청을 보낸다
    createButton.addEventListener('click', event => {
        body = JSON.stringify({
            title: document.getElementById('nickname').value,
            content: document.getElementById('password').value
        });
        function success() {
            alert('등록 완료되었습니다.');
            location.replace('/articles');
        };
        function fail() {
            alert('등록 실패했습니다.');
            location.replace('/articles');
        };

        httpRequest('POST','/api/articles', body, success, fail)
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        body = JSON.stringify({
            title: document.getElementById('nickname').value,
            content: document.getElementById('password').value
        })

        function success() {
            alert('수정 완료되었습니다.');
            location.replace(`/articles/${id}`);
        }

        function fail() {
            alert('수정 실패했습니다.');
            location.replace(`/articles/${id}`);
        }

        httpRequest('PUT',`/api/articles/${id}`, body, success, fail);
    });


