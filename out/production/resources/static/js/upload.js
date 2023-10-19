// More API functions here:
// https://github.com/googlecreativelab/teachablemachine-community/tree/master/libraries/image

// the link to your model provided by Teachable Machine export panel
const modelPATH = "/js/model/";

const modelURL = modelPATH + "model.json";
const metadataURL = modelPATH + "metadata.json";
const result = document.getElementById('result');

function loadFile(input) {
    var file = input.files[0];

    newImage = document.getElementById('show-img')

    newImage.src = modelPATH.createObjectURL(file);

    newImage.style.width = "300px";
    newImage.style.height = "300px";
    newImage.style.objectFit = "contain";

    var container = document.getElementById('image-show');
    container.appendChild(newImage);

    const img = document.getElementById('show-img');
    tmImage.load(modelURL, metadataURL).then(model => {
        function run(){
            model.predict(img).then(predictions => {
                console.log('Predictions: ', predictions);
                predictions.sort((a, b) => (b.probability - a.probability)); //내림차순으로 정렬 오름차순이면 a-b
                result.innerHTML = predictions[0].className + ' ' + parseInt(predictions[0].probability * 100) + '%';
            });
        }
        img.onload = function () {
             run();
        };
    });
};