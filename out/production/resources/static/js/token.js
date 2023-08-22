const token = searchParam('token')
const id = searchParam('id')


if (token) {
    localStorage.setItem("access_token", token)
}
if (id) {
    localStorage.setItem("user_email",id)
}

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}
