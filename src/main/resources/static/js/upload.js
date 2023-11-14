const result = document.getElementById('result');
let input = document.getElementById('input');
const modelPath = "/js/model/";
const model = await tf.loadLayersModel(modelPath + "model.json");

let name = "";

function handleImageUpload(event) {
    const fileInput = event.target;
    const file = fileInput.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function (e) {
            const img = new Image();
            img.src = e.target.result;

            img.onload = async function () {
                // 이미지를 TensorFlow.js 텐서로 변환
                let drinkImage = tf.browser.fromPixels(img);

                // 이미지 크기를 모델이 기대하는 크기로 조정
                drinkImage = tf.image.resizeBilinear(drinkImage, [150, 150]);

                // 배치 차원 추가
                drinkImage = tf.expandDims(drinkImage, 0);

                // 모델에 이미지 전달하여 예측 수행
                const prediction = model.predict(drinkImage);

                // 예측된 확률 배열을 가져오기
                const predictionArray = prediction.dataSync();

                // 콘솔에 예측된 확률 배열 출력
                console.log("Prediction Array:", predictionArray);

                // 가장 높은 확률을 가진 클래스의 인덱스 찾기
                const maxProbabilityIndex = predictionArray.indexOf(Math.max(...predictionArray));

                // 콘솔에 가장 높은 확률을 가진 클래스의 인덱스 출력
                console.log('Predicted class index:', maxProbabilityIndex);

                // 여기에 해당 클래스에 대한 정보를 출력하거나 다른 동작을 추가할 수 있습니다.
                // 예를 들어, 클래스에 대한 정보를 가져오는 함수를 호출하거나, 결과를 화면에 표시할 수 있습니다.
                displayClassInfo(maxProbabilityIndex);
                fetch('/js/drink.json')
                  .then(response => response.json())
                  .then(data => {
                    var coke = data.drinks[maxProbabilityIndex];
                    console.log(coke);
                    function success() {
                                location.replace(`/${coke}`);
                            };
                    function fail() {
                                location.replace(`/${coke}`);
                            };
                    httpRequest('GET',`/${coke}`, null, success, fail)
                  })
                  .catch(error => console.error('Error loading JSON:', error));
            };
        };

        reader.readAsDataURL(file);
    }
}
function displayClassInfo(classIndex) {
    // 여기에 해당 클래스에 대한 정보를 가져오고 표시하는 코드를 작성하세요.
    // 예를 들어, 클래스의 이름이나 설명을 가져와서 화면에 표시할 수 있습니다.
    console.log('Displaying information for class index:');
}
input.addEventListener('change', function(event){
    handleImageUpload(event);
});
