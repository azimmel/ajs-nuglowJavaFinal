//$(document).ready(function () {
    function validateDetailForm() {
        var x = document.forms["detailsForm"]["productId"].value;
        if (x <= 0 || x > 10000) {
            alert("ID must be in range between 1 and 10000.");
            return false;
        }
        x = document.forms["detailsForm"]["productName"].value;
        if (x === null || x === "" || x.length() < 3) {
            alert("Name must be filled in and must be longer than 3 characters");
            return false;
        }
        x = document.forms["detailsForm"]["type"].value;
        if (x === null || x === "" || x.length() < 3) {
            alert("Type must be filled in and must be longer than 3 characters");
            return false;
        }
        x = document.forms["detailsForm"]["price"].value;
        if (x <= 0.01 || x > 10000) {
            alert("Price must be between 0.01 and 10000.");
            return false;
        }
        x = document.forms["detailsForm"]["imgUrl"].value;
        var sub = x.substring(0,5);
        if (x === null || x === "" || x.length() < 10) {
            alert("Image Url Cannot be null or less than 10 characters");
            return false;
        }
        if(sub !== "imgs/"){
            alert("Image Url has to be be in the relative imgs folder in project. ex:'imgs/...'");
            return false;
        }
        x = document.forms["detailsForm"]["description"].value;
        if(x === null || x === "" || x.length() < 10){
            alert("Description cannot be null or less than 10 characters long.");
            return false;
        }
    }
    
    function validateCreateForm(){
        var x = document.forms["createForm"]["productName"].value;
        if (x === null || x === "" || x.length() < 3) {
            alert("Name must be filled in and must be longer than 3 characters");
            return false;
        }
        x = document.forms["createForm"]["type"].value;
        if (x === null || x === "" || x.length() < 3) {
            alert("Type must be filled in and must be longer than 3 characters");
            return false;
        }
        x = document.forms["createForm"]["price"].value;
        if (x <= 0.01 || x > 10000) {
            alert("Price must be between 0.01 and 10000.");
            return false;
        }
        x = document.forms["createForm"]["imgUrl"].value;
        var sub = x.substring(0,5);
        if (x === null || x === "" || x.length() < 10) {
            alert("Image Url Cannot be null or less than 10 characters");
            return false;
        }
        if(sub !== "imgs/"){
            alert("Image Url has to be be in the relative imgs folder in project. ex:'imgs/...'");
            return false;
        }
        x = document.forms["createForm"]["description"].value;
        if(x === null || x === "" || x.length() < 10){
            alert("Description cannot be null or less than 10 characters long.");
            return false;
        }
    }
//});

