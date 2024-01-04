window.onload = function () {
    httpGetRequest2('/api/userDetailPage');
    httpGetRequest('/api/userData');
};

function httpGetRequest(url){
    const headers = {
        Authorization: 'Bearer ' + localStorage.getItem('access_token'),
        'Content-Type': 'application/json',
    };
    fetch(url, {
        method: 'GET',
        headers: headers
    })
    .then(response => response.json())
    .then(data => {
        // Thymeleaf로 직접 데이터 추가
        const userDataContainer = document.getElementById('userDataContainer');
        const userHtml = `
                            <div>
                                <p>Email: ${data.email}</p>
                                <p>Nickname: ${data.nickname}</p>
                                <p>Password : ${data.password}</p>
                                <p>Role : ${data.role}</p>
                                <!-- 기타 사용자 정보 필드들을 추가 -->
                            </div>
                        `;
        userDataContainer.innerHTML = userHtml;
    })
    .catch(error => {
        console.error('Error fetching user data:', error);
    });
}

function httpGetRequest2(url){
    const headers = {
        Authorization: 'Bearer ' + localStorage.getItem('access_token'),
        'Content-Type': 'application/json',
    };
    fetch(url, {
        method: 'GET',
        headers: headers
    })
    .then(response => {
          if (!response.ok) {
              // 500 에러인 경우
              throw new Error('Internal Server Error');
          }
          return response.json();
      })
    .then(data => {
        // Thymeleaf로 직접 데이터 추가
        const userDataContainer = document.getElementById('userDataContainer2');
        const userHtml = `
                            <div>
                                <p>id: ${data.id}</p>
                                <p>name: ${data.name}</p>
                                <p>email : ${data.email}</p>
                                <p>birth : ${data.birth}</p>
                                <p>sex : ${data.sex}</p>
                                <p>address : ${data.address}</p>
                                <p>phoneNumber : ${data.phoneNumber}</p>
                                <p>cardNum : ${data.cardNum}</p>
                            </div>
                        `;
        userDataContainer.innerHTML = userHtml;
    })
    .catch(error => {
        alert("상세 정보가 없습니다. 상세 정보를 우선 입력해주세요")
        window.location.href = '/signup';
        console.error('Error fetching user data:', error);
    });
}