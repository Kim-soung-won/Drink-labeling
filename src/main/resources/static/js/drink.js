// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('drink-id').value;
        function success() {
            alert('삭제가 완료되었습니다.');
            location.replace('/drinkList');
        }

        function fail() {
            alert('삭제 실패했습니다.');
            location.replace('/drinkList');
        }

        httpRequest('DELETE',`/api/drink/${id}`, null, success, fail);
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let id = document.getElementById('drink-id').value;

        body = JSON.stringify({
            name: document.getElementById('name').value,
            cal: document.getElementById('cal').value,
            car: document.getElementById('car').value,
            pro: document.getElementById('pro').value,
            fat: document.getElementById('fat').value,
            na: document.getElementById('na').value,
            cafe: document.getElementById('cafe').value,
            gro: document.getElementById('gro').value,
            other: document.getElementById('other').value
        })

        function success() {
            alert('수정 완료되었습니다.');
            location.replace('/drinkList');
        }

        function fail() {
            alert('수정 실패했습니다.');
            location.replace('/drinkList');
        }

        httpRequest('PUT',`/api/drink/${id}`, body, success, fail);
    });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    // 등록 버튼을 클릭하면 /api/articles로 요청을 보낸다
    createButton.addEventListener('click', event => {
        body = JSON.stringify({
            name: document.getElementById('name').value,
            cal: document.getElementById('cal').value,
            car: document.getElementById('car').value,
            pro: document.getElementById('pro').value,
            fat: document.getElementById('fat').value,
            na: document.getElementById('na').value,
            cafe: document.getElementById('cafe').value,
            gro: document.getElementById('gro').value,
            other: document.getElementById('other').value
        });
        function success() {
            alert('등록 완료되었습니다.');
            location.replace('/drinkList');
        };
        function fail() {
            alert('등록 실패했습니다.');
            location.replace('/drinkList');
        };

        httpRequest('POST','/api/drink', body, success, fail)
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