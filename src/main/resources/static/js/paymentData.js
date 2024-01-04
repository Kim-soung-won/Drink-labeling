window.onload = function () {
    httpGetRequest2('/api/userDetailPage');
};

function httpGetRequest2(url){
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
        const name = document.getElementById('name');
        name.value = data.name
        const birth = document.getElementById('birth');
        birth.value = data.birth
        const address = document.getElementById('address');
        address.value = data.address
        const phoneNumber = document.getElementById('phone');
        phoneNumber.value = data.phoneNumber;
    })
    .catch(error => {
        console.error('Error fetching user data:', error);
    });
}