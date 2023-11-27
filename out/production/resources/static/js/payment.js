var IMP = window.IMP;
IMP.init('imp76806111') // 예: 'imp00000000a'


function generateUniqueNumber() {
    // 현재 시간을 이용하여 고유한 숫자 생성
    const timestamp = Date.now();

    // Math.random()을 이용하여 무작위 숫자 생성
    const randomValue = Math.random();

    // 위의 두 값을 조합하여 고유한 일련번호 생성
    const uniqueNumber = `${timestamp}-${randomValue}`;

    return uniqueNumber;
}

function requestPay() {
    let price = document.getElementById('price');
    let priceText = parseInt(price.innerText, 10);
    let quantity = parseInt(document.getElementById('quantity').value, 10);
    let drinkName = document.getElementById('drinkName');
    let drinkNameText = drinkName.innerText;
    let address = document.getElementById('address').value;
    let phone = document.getElementById('phone').value;
    let name = document.getElementById('name').value;

    let amount = priceText*quantity*6;
    console.log(amount)
    IMP.request_pay({
      pg: "kcp.{상점ID}",
      pay_method: "card",
      merchant_uid: generateUniqueNumber(),   // 주문번호
      name: drinkNameText,
      amount: amount,                         // 숫자 타입
      buyer_email: "dldbsghks8@gmail.com",
      buyer_name: name,
      buyer_tel: phone,
      buyer_addr: address,
      buyer_postcode: "01181"
    },
    function (rsp) { // callback
      if (rsp.success) {
              console.log(rsp);
          } else {
              console.log(rsp);
      }
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