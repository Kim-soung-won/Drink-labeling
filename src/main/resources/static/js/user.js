const accessToken = localStorage.getItem('access_token');

if(access_token){
    fetch('/get-id',{
        method: 'GET',
        headers:{
            'Authorization': `Bearer ${accessToken}`
        }
    })
    .then(response => response.json())
      .then(data => {
        const id = data.id;
        console.log('User ID:', id);
      })
      .catch(error => {
        console.error('Error:', error);
     });
}


const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        body = JSON.stringify({
            nickname: document.getElementById('nickname').value,
            password: document.getElementById('password').value
        });
        function success() {
            alert('등록 완료되었습니다.');
        };
        function fail() {
            alert('등록 실패했습니다.');
        };

        httpRequest('POST','/api/users', body, success, fail)
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        body = JSON.stringify({
            nickname: document.getElementById('nickname').value,
            password: document.getElementById('password').value
        })

        function success() {
            alert('수정 완료되었습니다.');
        }

        function fail() {
            alert('수정 실패했습니다.');
        }

        httpRequest('PUT','/api/users', body, success, fail);
    });
}

// key 즉 자신이 가진 쿠키를 가져오는 함수
function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        var dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(error => fail());
        } else {
            return fail();
        }
    });
}

