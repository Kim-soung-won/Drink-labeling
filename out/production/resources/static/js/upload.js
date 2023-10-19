const img = document.getElementById('img');
const result = document.getElementById('result');
let input = document.getElementById('input');
const modelPath = "/js/model/";
const modelURL = modelPath + "model.json";
const metadataURL = modelPath + "metadata.json";

tmImage.load(modelURL, metadataURL).then(model => {
    document.getElementById('loader').style.display = 'none';
    document.getElementById('status').innerHTML = "나의 모델 로딩 완료"

    function run() {
        model.predict(img).then(predictions => {
            console.log('Predictions: ', predictions);
            predictions.sort((a, b) => (b.probability - a.probability));
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
