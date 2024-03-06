const createButton = document.getElementById('saveButton');
var user_id = parseInt(searchParam('FJEOFEONVISAKODOAJ'));

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}
if (createButton) {
    createButton.addEventListener('click', event => {
        const name = document.getElementById('name').value.trim();
        const birth = document.getElementById('birth').value.trim();
        let sex = parseInt(document.getElementById('sex').value.trim());
        const phoneNumber = document.getElementById('phoneNumber').value.trim();
        const email = document.getElementById('email').value.trim();
        const address = document.getElementById('address').value.trim();
        const cardNum = document.getElementById('cardNum').value.trim();
        alert('ddd');
        alert(sex);
        if(sex%2===1) sex="male";
        else sex= "female";
        alert(sex);
        // 필드 값이 모두 존재하는지 확인
        if (!name || !birth || !sex || !phoneNumber || !email || !address || !user_id) {
            alert('모든 필드를 입력하세요.');
            return; // 빈 값이 하나라도 있으면 함수 종료
        }
        // HTTP 요청 보내기
        body = JSON.stringify({ name, birth, sex, phoneNumber, email, address, cardNum, user_id });
        function success() {
            alert('등록 완료되었습니다.');
            location.replace('/loginForm');
        };
        function fail() {
            alert('등록 실패했습니다.');
        };

        sendPostRequest('/api/first/userData/details', body, success, fail);
    });
}
function sendPostRequest(url, body, successCallback, errorCallback) {
    fetch(url, {
        method: 'POST',
        body: body,
        headers: {
            'Content-Type': 'application/json',
            // 다른 필요한 헤더들을 여기에 추가할 수 있습니다.
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        // 성공적인 응답 처리
        successCallback(data);
    })
    .catch(error => {
        // 에러 처리
        console.error('Fetch error:', error);
        alert(data);
        errorCallback(error);
    });
}



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
    alert(method);
    alert(body);
    const headers = {
        Authorization: 'Bearer ' + localStorage.getItem('access_token'),
        'Content-Type': 'application/json',
    };
    alert(headers);
    const options = {
        method: method,
        headers: headers,
    };
    alert(options);
    if (method !== 'GET') {
            options.body = body;
    }
    fetch(url, options).then(response => {
        if(!response.ok){
            alert("throw new Error");
        }
        alert(response.status);
        if (response.status === 200 || response.status === 201) {
            alert(response.status);
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