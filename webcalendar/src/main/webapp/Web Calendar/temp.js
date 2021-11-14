const months = ["January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"];

const daysOfWeek = ["Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"];


$(document).ready(function () {

    let date = new Date();
    let currMonth = date.getMonth();
    let currYear = date.getFullYear();

    for (let i = 0; i < months.length; i++) {
        $("#month").append(`<option name="${i}">${months[i]}</option>`)
    }

    for (let i = 0; i < 11; i++) {
        $("#year").append(`<option name="${currYear + i}">${currYear + i}</option>`)
    }

    $(`option[name=${currMonth}]`).attr("selected", "selected"); // Choose current month as default month.
    $(`option[name=${currYear}]`).attr("selected", "selected"); // Choose current year as default year.

    generateCal(currMonth, currYear); // Display the current month's days.

    $("#month").on('change', function () {
        let optName = $(this).find('option:selected').attr('name');
        console.log(optName);
        currMonth = Number(optName);
        generateCal(currMonth, currYear);
    });

    $("#year").on('change', function () {
        let optName = $(this).find('option:selected').attr('name');
        console.log(optName);
        currYear = Number(optName);
        generateCal(currMonth, currYear);
    });

    $(document).on('click', '.tas', (function () { // If user clicks on the textarea a prompt will be generated to get the reminder input and put it into the textarea.
        //console.log("In!")
        //console.log($(this).find("div").eq(0).html()); 
        $(this).empty();
        let text = "Enter Reminder for: " + $(this).parent().find("div").eq(0).html() + ", " + months[currMonth] + " " + $(this).parent().find("div").eq(1).html() + ", " + currYear;
        let defaultText = "Enter Reminder";
        let reminder = window.prompt(text, defaultText);
        if (reminder != null && reminder !== "") {
            let temp = "-" + reminder + ".\n";
            $(this).append(temp);
        }
    }));

    $(document).on('click', '.showbutton', function () {
        let content = $(this).parent().parent().find("textarea").val();
        if (content != null && content !== "") {
            let text = $(this).parent().parent().find("div").eq(0).html() + ", " + months[currMonth] + " " + $(this).parent().parent().find("div").eq(1).html() + ", " + currYear + "\n";
            alert(text + content);
        }
    })

    $.ajax({
        url: 'Controller',
        type: 'GET',
        success: function (response) {
            console.log(response);
        },
        error: function (jqXHR, exception) {
            var msg = '';
            if (jqXHR.status === 0) {
                msg = 'Not connected.\nVerify Network.';
            } else if (jqXHR.status == 404) {
                msg = 'Status 404: Requested page not found.';
            } else if (jqXHR.status == 500) {
                msg = 'Status 500: Internal Server Error.';
            } else if (exception === 'parsererror') {
                msg = 'Requested JSON parse failed.';
            } else if (exception === 'timeout') {
                msg = 'Time out error.';
            } else if (exception === 'abort') {
                msg = 'Ajax request aborted.';
            } else {
                msg = 'Uncaught Error.\n' + jqXHR.responseText;
            }
            console.log(msg);
        }
    });
});

function generateCal(month, year) {
    $("#div1").empty();
    let temp = getDaysOfMonth(month, year);
    for (let i = 0; i < temp.length; i++) {
        //    console.log(temp[i]);
        $("#div1").append(`<button id="butt${(i + 1)}"></button>`);
        $(`#butt${i + 1}`).append(`<div>${daysOfWeek[temp[i].getDay()].toUpperCase()}</div>`);
        $(`#butt${i + 1}`).append(`<div>${i + 1}</div>`);
        $(`#butt${i + 1}`).append("<break></break>");
        $(`#butt${i + 1}`).append("<article class='tas' placeholder='CLICK TO ADD REMINDER'>Click Here</article>");
        $(`#butt${i + 1}`).append(`<div id="div3"><button class="showbutton">Show All Reminders</button></div>`);
        //    console.log((i + 1) + " : " + daysOfWeek[temp[i].getDay()]);
    }
}

// Month is 0-indexed.
function getDaysOfMonth(month, year) {
    let d = new Date(year, month, 1);
    let days = [];
    while (d.getMonth() === month) {
        days.push(new Date(d));
        d.setDate(d.getDate() + 1); // The setDate() method changes the day of the month of a given Date instance, based on local time.
    }
    return days;
}