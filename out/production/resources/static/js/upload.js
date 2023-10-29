const img = document.getElementById('img');
const result = document.getElementById('result');
let input = document.getElementById('input');
const modelPath = "/js/model/";
const modelURL = modelPath + "model.json";
const metadataURL = modelPath + "metadata.json";
let name = "";

tmImage.load(modelURL, metadataURL).then(model => {
    document.getElementById('loader').style.display = 'none';
    document.getElementById('status').innerHTML = "나의 모델 로딩 완료"

    function run() {
        model.predict(img).then(predictions => {
            predictions.sort((a, b) => (b.probability - a.probability));
            name = predictions[0].className;
            result.innerHTML = predictions[0].className + ' ' + parseInt(predictions[0].probability * 100) + '%';
        });
    }

    input.addEventListener('change', (e) => {
        img.src = URL.createObjectURL(e.target.files[0]);
    }, false);

    img.onload = function () {
        run();
    };
});


const showButton = document.getElementById('show-data');

if (showButton) {
    showButton.addEventListener('click', event => {
        function success() {
            location.replace(`/upload/${name}`);
        };
        function fail() {
            location.replace(`/upload/${name}`);
        };

        httpRequest('GET',`/upload/${name}`, null, success, fail)
    });
}
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