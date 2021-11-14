
$(document).ready(function () {
	$("#loginForm").submit(function (e) {
        e.preventDefault(e);
    });
    $("#submit").on('click', function () {
        let checkUser = checkUsername();
        let checkWithRegex = checkPasswordRegex();
        let checkPass = checkPassword();

        if (checkUser && checkWithRegex && checkPass) {
            $("#nameAlert").text('');
            $("#passAlert").text('');
            $("#loginForm").unbind('submit').submit();
        }
        else if (checkUser) $("#nameAlert").text('');
        else if (checkPass && checkWithRegex) $("#passAlert").text('');
    });
})

function checkUsername() {

    if ($("#username").val() == "") {
        $("#nameAlert").text('');
        $("#nameAlert").text('Please enter username!');
        return false;
    }
    return true;
}

function checkPassword() {
    if ($("#password").val() == "") {
        $("#passAlert").text('');
        $("#passAlert").text('Please enter password!');
        return false;
    }
    return true;
}

function checkPasswordRegex() {
    let value = $("#password").val();
    //Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character:
    let regex = new RegExp('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$');

    if (!regex.test(value)) {
        $("#passAlert").text('');
        $("#passAlert").text('Password should contain minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character.');
        return false;
    }
    return true;
}